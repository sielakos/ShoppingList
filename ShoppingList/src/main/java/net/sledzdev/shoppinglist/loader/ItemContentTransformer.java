package net.sledzdev.shoppinglist.loader;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.common.collect.ImmutableList;

import net.sledzdev.shoppinglist.model.ShoppingItemBuilder;
import net.sledzdev.shoppinglist.content.ItemsTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mariusz on 22.12.13.
 */
public class ItemContentTransformer implements ContentTransformer<ShoppingItemBuilder> {

    @Override
    public List<ShoppingItemBuilder> transformCursor(Cursor cursor) {
        List<ShoppingItemBuilder> items = new ArrayList<ShoppingItemBuilder>();

        while (cursor.moveToNext()) {
            ShoppingItemBuilder builder = new ShoppingItemBuilder();
            builder.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ItemsTable.C_ID)))
                   .setName(cursor.getString(cursor.getColumnIndexOrThrow(ItemsTable.C_NAME)))
                   .setList_id(cursor.getLong(cursor.getColumnIndexOrThrow(ItemsTable.C_LIST_ID)))
                   .setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(ItemsTable.C_PRICE)));
            items.add(builder);
        }

        return ImmutableList.copyOf(items);
    }

    @Override
    public ContentValues transformValue(ShoppingItemBuilder value) {
        return null;
    }
}
