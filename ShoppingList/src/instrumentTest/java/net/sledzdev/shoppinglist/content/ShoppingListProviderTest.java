package net.sledzdev.shoppinglist.content;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;

/**
 * Created by Mariusz on 30.11.13.
 */
public class ShoppingListProviderTest extends ProviderTestCase2<ShoppingListProvider> {

    public ShoppingListProviderTest() {
        super(ShoppingListProvider.class, ShoppingProviderContract.AUTHORITY);
    }

    public void testDeleteItem() throws Exception {
        ContentResolver contentResolver = getMockContentResolver();

        Uri itemUri = insertItem(contentResolver);

        Cursor cursor = contentResolver.query(itemUri, null, null, null, null);
        assertTrue(cursor.moveToFirst());

        int rowsDeleted = contentResolver.delete(itemUri, null, null);
        assertEquals(1, rowsDeleted);

        cursor = contentResolver.query(itemUri, null, null, null, null);
        assertFalse(cursor.moveToFirst());

        insertItem(contentResolver);
        insertItem(contentResolver, "śmietana", 2, 4.5);

        cursor = contentResolver.query(ShoppingProviderContract.ITEMS_URI, null, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertTrue(cursor.moveToNext());

        rowsDeleted = contentResolver.delete(ShoppingProviderContract.ITEMS_URI, ItemsTable.C_NAME + " = ?", new String[] {"czekolada"});
        assertEquals(1, rowsDeleted);

        cursor = contentResolver.query(ShoppingProviderContract.ITEMS_URI, null, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("śmietana", cursor.getString(cursor.getColumnIndexOrThrow(ItemsTable.C_NAME)));
        assertFalse(cursor.moveToNext());
    }

    public void testDeleteList() throws Exception {
        ContentResolver contentResolver = getMockContentResolver();

        Uri listUri = insertList(contentResolver);

        Cursor cursor = contentResolver.query(listUri, new String[]{ListsTable.C_NAME}, null, null, null);
        assertTrue(cursor.moveToFirst());

        int rowsDeleted = contentResolver.delete(listUri, null, null);
        assertEquals(1, rowsDeleted);

        cursor = contentResolver.query(listUri, new String[]{ListsTable.C_NAME}, null, null, null);
        assertFalse(cursor.moveToFirst());

        insertList(contentResolver);
        insertList(contentResolver);

        cursor = contentResolver.query(ShoppingProviderContract.LIST_URI, null, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertTrue(cursor.moveToNext());

        rowsDeleted = contentResolver.delete(ShoppingProviderContract.LIST_URI, null, null);
        assertEquals(2, rowsDeleted);

        cursor = contentResolver.query(listUri, new String[]{ListsTable.C_NAME}, null, null, null);
        assertFalse(cursor.moveToFirst());
    }

    public void testUpdateList() throws Exception {
        ContentResolver contentResolver = getMockContentResolver();

        Uri listUri = insertList(contentResolver);
        assertNotNull(listUri);

        ContentValues updates = new ContentValues();
        updates.put(ListsTable.C_NAME, "ala");

        int rowsUpdated = contentResolver.update(listUri, updates, null, null);
        assertEquals(1, rowsUpdated);

        Cursor cursor = contentResolver.query(listUri, new String[]{ListsTable.C_NAME}, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("ala", cursor.getString(cursor.getColumnIndexOrThrow(ListsTable.C_NAME)));
        assertFalse(cursor.moveToNext());
    }

    public void testItemsUpdate() throws Exception {
        ContentResolver contentResolver = getMockContentResolver();

        Uri itemUri = insertItem(contentResolver);
        assertNotNull(itemUri);

        Cursor cursor = contentResolver.query(itemUri, null, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("czekolada", cursor.getString(cursor.getColumnIndexOrThrow(ItemsTable.C_NAME)));
        assertEquals(1, cursor.getInt(cursor.getColumnIndexOrThrow(ItemsTable.C_LIST_ID)));
        assertEquals(3.1, cursor.getDouble(cursor.getColumnIndexOrThrow(ItemsTable.C_PRICE)));

        ContentValues updates = new ContentValues();
        updates.put(ItemsTable.C_NAME, "czekolada mleczna");

        int rowsUpdated = contentResolver.update(itemUri, updates, null, null);
        assertEquals(1, rowsUpdated);

        cursor = contentResolver.query(itemUri, null, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("czekolada mleczna", cursor.getString(cursor.getColumnIndexOrThrow(ItemsTable.C_NAME)));
        assertEquals(1, cursor.getInt(cursor.getColumnIndexOrThrow(ItemsTable.C_LIST_ID)));
        assertEquals(3.1, cursor.getDouble(cursor.getColumnIndexOrThrow(ItemsTable.C_PRICE)));

        updates = new ContentValues();
        updates.put(ItemsTable.C_PRICE, 3.14);

        rowsUpdated = contentResolver.update(itemUri, updates, ItemsTable.C_NAME + " = ?", new String[] {"czekolada mleczna"});
        assertEquals(1, rowsUpdated);

        cursor = contentResolver.query(itemUri, null, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("czekolada mleczna", cursor.getString(cursor.getColumnIndexOrThrow(ItemsTable.C_NAME)));
        assertEquals(1, cursor.getInt(cursor.getColumnIndexOrThrow(ItemsTable.C_LIST_ID)));
        assertEquals(3.14, cursor.getDouble(cursor.getColumnIndexOrThrow(ItemsTable.C_PRICE)));
    }

    public void testInsertAndQueryItems() throws Exception {
        ContentResolver contentResolver = getMockContentResolver();

        Uri itemUri = insertItem(contentResolver);
        assertNotNull(itemUri);

        Cursor cursor = contentResolver.query(itemUri, null, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("czekolada", cursor.getString(cursor.getColumnIndexOrThrow(ItemsTable.C_NAME)));
        assertEquals(1, cursor.getInt(cursor.getColumnIndexOrThrow(ItemsTable.C_LIST_ID)));
        assertEquals(3.1, cursor.getDouble(cursor.getColumnIndexOrThrow(ItemsTable.C_PRICE)));
    }

    private Uri insertItem(ContentResolver contentResolver) {
        return insertItem(contentResolver, "czekolada", 1, 3.1);
    }

    private Uri insertItem(ContentResolver contentResolver, String name, long list_id, double price) {
        ContentValues values = new ContentValues();
        values.put(ItemsTable.C_NAME, name);
        values.put(ItemsTable.C_LIST_ID, list_id);
        values.put(ItemsTable.C_PRICE, price);

        return contentResolver.insert(ShoppingProviderContract.ITEMS_URI, values);
    }

    public void testInsertAndQueryLists() throws Exception {
        ContentResolver contentResolver = getMockContentResolver();

        Uri listUri = insertList(contentResolver);
        assertNotNull(listUri);

        Cursor cursor = contentResolver.query(listUri, new String[]{ListsTable.C_NAME}, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("tesco", cursor.getString(cursor.getColumnIndexOrThrow(ListsTable.C_NAME)));
        assertFalse(cursor.moveToNext());

        cursor = contentResolver.query(ShoppingProviderContract.LIST_URI, new String[]{ListsTable.C_NAME}, null, null, null);
        assertTrue(cursor.moveToFirst());
        assertEquals("tesco", cursor.getString(cursor.getColumnIndexOrThrow(ListsTable.C_NAME)));
        assertFalse(cursor.moveToNext());
    }

    private Uri insertList(ContentResolver contentResolver) {
        ContentValues values = new ContentValues();
        values.put(ListsTable.C_NAME, "tesco");

        return contentResolver.insert(ShoppingProviderContract.LIST_URI, values);
    }

    public void testInvalidUri() throws Exception {
        ContentResolver contentResolver = getMockContentResolver();

        boolean thrown = false;
        try {
            contentResolver.query(Uri.parse("content://"+ShoppingProviderContract.AUTHORITY+"/browsers"), null, null, null, null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    public void testInvalidProjection() throws Exception {
        ContentResolver contentResolver = getMockContentResolver();

        boolean thrown = false;
        try {
            contentResolver.query(ShoppingProviderContract.LIST_URI, new String[]{"ala"}, null, null, null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            contentResolver.query(ContentUris.withAppendedId(ShoppingProviderContract.LIST_URI, 1), new String[]{"ala"}, null, null, null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            contentResolver.query(ShoppingProviderContract.ITEMS_URI, new String[]{"ala"}, null, null, null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            contentResolver.query(ContentUris.withAppendedId(ShoppingProviderContract.ITEMS_URI, 1), new String[]{"ala"}, null, null, null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    public void testIncorrectInsertAndUpdate() throws Exception {
        ContentResolver contentResolver = getMockContentResolver();

        ContentValues contentValues = new ContentValues();
        contentValues.put("ala", 12);

        boolean thrown = false;
        try {
            contentResolver.insert(ShoppingProviderContract.ITEMS_URI, contentValues);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            contentResolver.insert(ShoppingProviderContract.LIST_URI, contentValues);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            contentResolver.insert(ContentUris.withAppendedId(ShoppingProviderContract.LIST_URI, 1), contentValues);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            contentResolver.update(ContentUris.withAppendedId(ShoppingProviderContract.ITEMS_URI, 1), contentValues, null, null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            contentResolver.update(ContentUris.withAppendedId(ShoppingProviderContract.LIST_URI, 1), contentValues, null, null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            contentResolver.update(ShoppingProviderContract.ITEMS_URI, contentValues, null, null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            contentResolver.update(ShoppingProviderContract.LIST_URI, contentValues, null, null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

}
