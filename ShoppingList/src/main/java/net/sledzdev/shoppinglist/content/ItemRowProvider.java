package net.sledzdev.shoppinglist.content;

import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.Arrays;

/**
 * Created by Mariusz on 23.11.13.
 */
public class ItemRowProvider extends RowUriProvider {

    public ItemRowProvider(int id, SQLiteOpenHelper openHelper) {
        super(ItemsTable.TABLE_NAME, ItemsTable.C_ID, id, openHelper);
    }

    @Override
    public String getType(Uri uri) {
        return ShoppingProviderContract.ITEMS_ROW_TYPE;
    }

    @Override
    public boolean checkProjection(String[] projection) {
        return Arrays.asList(ItemsTable.ALLOWED_COLUMNS).containsAll(Arrays.asList(projection));
    }
}
