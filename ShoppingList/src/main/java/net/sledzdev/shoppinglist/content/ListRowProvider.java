package net.sledzdev.shoppinglist.content;

import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by Mariusz on 23.11.13.
 */
public class ListRowProvider extends RowUriProvider {

    public ListRowProvider(int id, SQLiteOpenHelper openHelper) {
        super(ListsTable.TABLE_NAME, ListsTable.C_ID, id, openHelper);
    }

    @Override
    public String getType(Uri uri) {
        return ShoppingProviderContract.LIST_ROW_TYPE;
    }
}
