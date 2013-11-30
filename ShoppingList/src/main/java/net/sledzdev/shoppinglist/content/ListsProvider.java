package net.sledzdev.shoppinglist.content;

import android.content.ContentUris;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.Arrays;

/**
 * Created by Mariusz on 23.11.13.
 */
public class ListsProvider extends TableUriProvider {

    public ListsProvider(int id, SQLiteOpenHelper openHelper) {
        super(ListsTable.TABLE_NAME, id, openHelper);
    }

    @Override
    public String getType(Uri uri) {
        return ShoppingProviderContract.LISTS_TYPE;
    }

    @Override
    public Uri getUriForId(long id) {
        return ContentUris.withAppendedId(ShoppingProviderContract.LIST_URI, id);
    }

    @Override
    public boolean checkProjection(String[] projection) {
        return Arrays.asList(ListsTable.ALLOWED_COLUMNS).containsAll(Arrays.asList(projection));
    }
}
