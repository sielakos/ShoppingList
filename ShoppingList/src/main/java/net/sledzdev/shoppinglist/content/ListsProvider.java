package net.sledzdev.shoppinglist.content;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

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
}
