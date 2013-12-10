package net.sledzdev.shoppinglist.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.SparseArray;

/**
 * Created by Mariusz on 23.11.13.
 */
public class ShoppingListProvider extends ContentProvider {

    private static final int LISTS = 10;
    private static final int LIST_ID = 11;
    private static final int ITEMS = 20;
    private static final int ITEM_ID = 21;
    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(ShoppingProviderContract.AUTHORITY, ListsTable.TABLE_NAME, LISTS);
        URI_MATCHER.addURI(ShoppingProviderContract.AUTHORITY, ListsTable.TABLE_NAME + "/#", LIST_ID);
        URI_MATCHER.addURI(ShoppingProviderContract.AUTHORITY, ItemsTable.TABLE_NAME, ITEMS);
        URI_MATCHER.addURI(ShoppingProviderContract.AUTHORITY, ItemsTable.TABLE_NAME + "/#", ITEM_ID);
    }

    private SQLiteOpenHelper openHelper;
    private SparseArray<UriContentProvider> contentRouter;

    @Override
    public boolean onCreate() {
        openHelper = new DatabaseHelper(getContext());

        contentRouter = new SparseArray<UriContentProvider>();
        contentRouter.put(LISTS, new ListsProvider(LISTS, openHelper));
        contentRouter.put(LIST_ID, new ListRowProvider(LIST_ID, openHelper));
        contentRouter.put(ITEMS, new ItemsProvider(LIST_ID, openHelper));
        contentRouter.put(ITEM_ID, new ItemRowProvider(LIST_ID, openHelper));

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return matchUriAndGetUriContentProvider(uri).query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        return matchUriAndGetUriContentProvider(uri).getType(uri);
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return matchUriAndGetUriContentProvider(uri).insert(uri, values);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return matchUriAndGetUriContentProvider(uri).delete(uri, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return matchUriAndGetUriContentProvider(uri).update(uri, values, selection, selectionArgs);
    }

    private UriContentProvider matchUriAndGetUriContentProvider(Uri uri) {
        int id = URI_MATCHER.match(uri);
        return getUriContentProviderOrThrow(id);
    }

    private UriContentProvider getUriContentProviderOrThrow(int id) {
        UriContentProvider uriContentProvider = contentRouter.get(id);
        if (uriContentProvider == null) {
            throw new IllegalArgumentException("There is no entry with id: " + id);
        }
        return uriContentProvider;
    }
}
