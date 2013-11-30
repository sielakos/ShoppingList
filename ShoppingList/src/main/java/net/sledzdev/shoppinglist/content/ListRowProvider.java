package net.sledzdev.shoppinglist.content;

import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.Arrays;

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

    @Override
    public boolean checkProjection(String[] projection) {
        return Arrays.asList(ListsTable.ALLOWED_COLUMNS).containsAll(Arrays.asList(projection));
    }
}
