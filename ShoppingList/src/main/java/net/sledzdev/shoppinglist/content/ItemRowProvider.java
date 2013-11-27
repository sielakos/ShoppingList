package net.sledzdev.shoppinglist.content;

import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

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
}
