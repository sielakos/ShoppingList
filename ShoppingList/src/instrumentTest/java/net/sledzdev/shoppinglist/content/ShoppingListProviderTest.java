package net.sledzdev.shoppinglist.content;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.test.ProviderTestCase2;

/**
 * Created by Mariusz on 30.11.13.
 */
public class ShoppingListProviderTest extends ProviderTestCase2<ShoppingListProvider> {

    public ShoppingListProviderTest() {
        super(ShoppingListProvider.class, ShoppingProviderContract.AUTHORITY);
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
    }

    public void testInsertAndQuery() throws Exception {
        ContentResolver contentResolver = getMockContentResolver();

        ContentValues contentValues = new ContentValues();

        contentResolver.insert(ShoppingProviderContract.ITEMS_URI, contentValues);
    }

}
