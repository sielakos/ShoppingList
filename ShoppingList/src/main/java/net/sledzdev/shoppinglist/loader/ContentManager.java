package net.sledzdev.shoppinglist.loader;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import net.sledzdev.shoppinglist.adapter.DataModel;
import net.sledzdev.shoppinglist.adapter.ListMapDataModel;
import net.sledzdev.shoppinglist.adapter.ShoppingList;
import net.sledzdev.shoppinglist.content.ShoppingProviderContract;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Created by Mariusz on 21.12.13.
 */
public class ContentManager {

    protected static int THREADS = 1;

    private ListeningExecutorService service;
    private Context context;
    private ContentResolver resolver;
    private ContentTransformer<ShoppingList> listsCursorTransformer;

    public ContentManager(Context context) {
        service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(THREADS));
        this.context = context;
        resolver = context.getContentResolver();
    }

    public ListenableFuture<DataModel<ShoppingList>> loadShoppingListsModel() {
        return service.submit(new Callable<DataModel<ShoppingList>>() {
            @Override
            public DataModel<ShoppingList> call() throws Exception {
                Cursor cursor = resolver.query(ShoppingProviderContract.LIST_URI, null, null, null, null);
                List<ShoppingList> list = listsCursorTransformer.transformCursor(cursor);
                return new ListMapDataModel<ShoppingList>(list);
            }
        });
    }

    public ListenableFuture<Integer> removeList(final ShoppingList list) {
        return service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Uri uri = ContentUris.withAppendedId(ShoppingProviderContract.LIST_URI, list.getId());
                return resolver.delete(uri, null, null);
            }
        });
    }

    public void addList(final ShoppingList list) {
        service.submit(new Runnable() {
            @Override
            public void run() {
                ContentValues values = listsCursorTransformer.transformValue(list);
                resolver.insert(ShoppingProviderContract.LIST_URI, values);
            }
        });
    }

    //TODO: add items handling
    //TODO: add events
    //TODO: test this class

}
