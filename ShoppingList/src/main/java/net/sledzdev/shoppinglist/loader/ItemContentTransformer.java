package net.sledzdev.shoppinglist.loader;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.google.common.collect.ImmutableList;

import net.sledzdev.shoppinglist.content.ItemsTable;
import net.sledzdev.shoppinglist.model.ShoppingItem;
import net.sledzdev.shoppinglist.model.ShoppingItemBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mariusz on 27.12.13.
 */
public class ItemContentTransformer implements ContentTransformer<ShoppingItem> {

    @Override
    public List<ShoppingItem> transformCursor(Cursor cursor) {
        List<ShoppingItem> items = new ArrayList<ShoppingItem>();

        while (cursor.moveToNext()) {
            items.add(getShoppingItem(cursor));
        }

        return ImmutableList.copyOf(items);
    }

    protected ShoppingItem getShoppingItem(Cursor cursor) {
        ShoppingItemBuilder builder = new ShoppingItemBuilder();
        builder.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ItemsTable.C_ID)))
               .setList_id(cursor.getLong(cursor.getColumnIndexOrThrow(ItemsTable.C_LIST_ID)))
               .setName(cursor.getString(cursor.getColumnIndexOrThrow(ItemsTable.C_NAME)))
               .setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(ItemsTable.C_PRICE)));
        return builder.createShoppingItem();
    }

    @Override
    public ContentValues transformValue(ShoppingItem item) {
        ContentValues values = new ContentValues();

        values.put(ItemsTable.C_NAME, item.name);
        values.put(ItemsTable.C_PRICE, item.price);
        values.put(ItemsTable.C_LIST_ID, item.list.getId());

        return values;
    }
}
