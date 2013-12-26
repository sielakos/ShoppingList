package net.sledzdev.shoppinglist.loader;

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
import net.sledzdev.shoppinglist.model.ShoppingList;
import net.sledzdev.shoppinglist.content.ShoppingProviderContract;
import net.sledzdev.shoppinglist.model.ShoppingListFactory;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Created by Mariusz on 21.12.13.
 */
public class ContentManager {

    private static ContentManager instance;
    protected static int THREADS = 1;

    private ListeningExecutorService service;
    private Context context;
    protected ContentResolver resolver;
    private ContentTransformer<ShoppingList> shoppingListContentTransformer;

    ContentManager(Context context) {
        service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(THREADS));
        this.context = context;
        resolver = initResolver(context);
        shoppingListContentTransformer = initShoppingListTransformer();
    }

    public static ContentManager createContentManager(Context context) {
        if (instance == null) {
            instance = new ContentManager(context);
        }
        return instance;
    }

    public static Optional<ContentManager> getExistingManager() {
        return Optional.fromNullable(instance);
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
                Cursor cursor = resolver.query(ShoppingProviderContract.LIST_URI, null, null, null, null);
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
                Cursor cursor = resolver.query(uri, null, null, null, null);
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
                return resolver.delete(uri, null, null);
            }
        });
    }

    public void save(final ShoppingList list) {
        service.submit(new Runnable() {
            @Override
            public void run() {
                ContentValues values = shoppingListContentTransformer.transformValue(list);
                if (list.isNewList()) {
                    Uri uri = resolver.insert(ShoppingProviderContract.LIST_URI, values);
                    addListToFactory(uri, list);
                } else {
                    resolver.update(
                        ContentUris.withAppendedId(ShoppingProviderContract.LIST_URI, list.getId()),
                        values,
                        null,
                        null
                    );
                }
            }
        });
    }

    protected void addListToFactory(Uri uri, ShoppingList list) {
        list.setId(Long.parseLong(uri.getLastPathSegment()));
        ShoppingListFactory.putList(list);
    }

    //TODO: add items handling
    //TODO: add events
    //TODO: test this class
}
