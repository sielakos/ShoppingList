package net.sledzdev.shoppinglist.loader;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.common.collect.ImmutableList;

import net.sledzdev.shoppinglist.model.ShoppingList;
import net.sledzdev.shoppinglist.content.ListsTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mariusz on 21.12.13.
 */
public class ListsContentTransformer implements ContentTransformer<ShoppingList> {

    @Override
    public List<ShoppingList> transformCursor(Cursor cursor) {
        List<ShoppingList> lists = new ArrayList<ShoppingList>();

        while(cursor.moveToNext()) {
            lists.add(ShoppingList.createShoppingList(
                    cursor.getLong(cursor.getColumnIndexOrThrow(ListsTable.C_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ListsTable.C_NAME))
            ));
        }

        return ImmutableList.copyOf(lists);
    }

    @Override
    public ContentValues transformValue(ShoppingList value) {
        ContentValues values = new ContentValues();
        if (!value.isNewList()) {
            values.put(ListsTable.C_ID, value.id);
        }
        values.put(ListsTable.C_NAME, value.name);
        return values;
    }
}
