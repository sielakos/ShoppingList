package net.sledzdev.shoppinglist.manager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import net.sledzdev.shoppinglist.adapter.DataModel;
import net.sledzdev.shoppinglist.adapter.ListMapDataModel;
import net.sledzdev.shoppinglist.content.ItemsTable;
import net.sledzdev.shoppinglist.content.ShoppingProviderContract;
import net.sledzdev.shoppinglist.model.ShoppingItem;
import net.sledzdev.shoppinglist.model.ShoppingList;
import net.sledzdev.shoppinglist.model.ShoppingListFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Mariusz on 21.12.13.
 */
public class ContentManager {

    protected static int THREADS = 1;
    private static Map<Context, ContentManager> instancesMap = new HashMap<Context, ContentManager>();
    protected ContentResolver resolver;
    private ListeningExecutorService service;
    private Context context;
    private ContentTransformer<ShoppingList> shoppingListContentTransformer;
    private ContentTransformer<ShoppingItem> shoppingItemContentTransformer;
    private Executor executor;

    ContentManager(Context context) {
        service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(THREADS));
        this.context = context;
        resolver = initResolver(context);
        shoppingListContentTransformer = initShoppingListTransformer();
        shoppingItemContentTransformer = initItemContentTransformer();
        instancesMap.put(context, this);
    }

    //This is here only, so MockContentManager could work properly.
    private ContentResolver getContentResolver() {
        if (resolver == null) {
            resolver = initResolver(context);
        }
        return resolver;
    }

    public static ContentManager createContentManager(Context context) {
        if (instancesMap.containsKey(context)) {
            return instancesMap.get(context);
        }
        return new ContentManager(context);
    }

    public static Optional<ContentManager> getExistingManager() {
        Iterator<ContentManager> iterator = instancesMap.values().iterator();
        if (iterator.hasNext()) {
            return Optional.fromNullable(iterator.next());
        }
        return Optional.absent();
    }

    private ItemContentTransformer initItemContentTransformer() {
        return new ItemContentTransformer();
    }

    protected ContentResolver initResolver(Context context) {
        return context.getContentResolver();
    }

    protected ContentTransformer<ShoppingList> initShoppingListTransformer() {
        return new ListsContentTransformer();
    }

    public ListenableFuture<DataModel<ShoppingList>> loadShoppingListsModel() {
        return service.submit(new Callable<DataModel<ShoppingList>>() {
            @Override
            public DataModel<ShoppingList> call() throws Exception {
                Cursor cursor = getContentResolver().query(ShoppingProviderContract.LIST_URI, null, null, null, null);
                List<ShoppingList> list = shoppingListContentTransformer.transformCursor(cursor);
                return new ListMapDataModel<ShoppingList>(list);
            }
        });
    }

    public ListenableFuture<Optional<ShoppingList>> getList(final long id) {
        Optional<ShoppingList> list = ShoppingListFactory.get(id);
        if (list.isPresent()) {
            return Futures.immediateFuture(list);
        }

        return service.submit(new Callable<Optional<ShoppingList>>() {
            @Override
            public Optional<ShoppingList> call() throws Exception {
                Uri uri = ContentUris.withAppendedId(ShoppingProviderContract.LIST_URI, id);
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                List<ShoppingList> list = shoppingListContentTransformer.transformCursor(cursor);
                if (list.size() > 0) {
                    return Optional.of(list.get(0));
                }
                return Optional.absent();
            }
        });
    }

    public ListenableFuture<Integer> remove(final ShoppingList list) {
        return service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Uri uri = ContentUris.withAppendedId(ShoppingProviderContract.LIST_URI, list.getId());
                return getContentResolver().delete(uri, null, null);
            }
        });
    }

    public ListenableFuture<Uri> save(final ShoppingList list) {
        return service.submit(new Callable<Uri>() {
            @Override
            public Uri call() throws Exception {
                ContentValues values = shoppingListContentTransformer.transformValue(list);
                if (list.isNewList()) {
                    Uri uri = getContentResolver().insert(ShoppingProviderContract.LIST_URI, values);
                    addListToFactory(uri, list);
                    return uri;
                } else {
                    getContentResolver().update(
                            ContentUris.withAppendedId(ShoppingProviderContract.LIST_URI, list.getId()),
                            values,
                            null,
                            null
                    );
                    return ContentUris.withAppendedId(ShoppingProviderContract.LIST_URI, list.getId());
                }
            }
        });
    }

    protected void addListToFactory(Uri uri, ShoppingList list) {
        list.setId(Long.parseLong(uri.getLastPathSegment()));
        list.setNewList(false);
        ShoppingListFactory.putList(list);
    }

    public ListenableFuture save(final ShoppingItem item) {
        return service.submit(new Runnable() {
            @Override
            public void run() {
                ContentValues values = shoppingItemContentTransformer.transformValue(item);
                if (item.newItem) {
                    insertNewItem(values, item);
                } else {
                    updateOldItem(values, item);
                }
            }
        });
    }

    protected void insertNewItem(ContentValues values, ShoppingItem item) {
        Uri uri = getContentResolver().insert(ShoppingProviderContract.ITEMS_URI, values);
        long id = Long.parseLong(uri.getLastPathSegment());
        item.newItem = false;
        item.id = id;
    }

    protected void updateOldItem(ContentValues values, ShoppingItem item) {
        Uri uri = ContentUris.withAppendedId(ShoppingProviderContract.ITEMS_URI, item.id);
        getContentResolver().update(uri, values, null, null);
    }

    public ListenableFuture<Integer> remove(final ShoppingItem item) {
        return service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Uri uri = ContentUris.withAppendedId(ShoppingProviderContract.ITEMS_URI, item.id);
                return getContentResolver().delete(uri, null, null);
            }
        });
    }

    public ListenableFuture<DataModel<ShoppingItem>> loadItems(final long list_id) {
        return service.submit(new Callable<DataModel<ShoppingItem>>() {
            @Override
            public DataModel<ShoppingItem> call() throws Exception {
                Cursor cursor = getContentResolver().query(ShoppingProviderContract.ITEMS_URI, null,
                        ItemsTable.C_LIST_ID + " = ?", new String[]{list_id + ""}, null);
                List<ShoppingItem> list = shoppingItemContentTransformer.transformCursor(cursor);
                return new ListMapDataModel<ShoppingItem>(list);
            }
        });
    }

    public ListenableFuture<DataModel<ShoppingItem>> loadItems(final ShoppingList list) {
        return loadItems(list.getId());
    }
}
