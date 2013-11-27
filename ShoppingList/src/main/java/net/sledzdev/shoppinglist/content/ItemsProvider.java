package net.sledzdev.shoppinglist.content;

import android.content.ContentUris;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by Mariusz on 23.11.13.
 */
public class ItemsProvider extends TableUriProvider {

    public ItemsProvider(int id, SQLiteOpenHelper openHelper) {
        super(ItemsTable.TABLE_NAME, id, openHelper);
    }

    @Override
    public String getType(Uri uri) {
        return ShoppingProviderContract.ITEMS_TYPE;
    }

    @Override
    public Uri getUriForId(long id) {
        return ContentUris.withAppendedId(ShoppingProviderContract.ITEMS_URI, id);
    }
}
