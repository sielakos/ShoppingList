package net.sledzdev.shoppinglist.content;

import android.net.Uri;

/**
 * Created by Mariusz on 23.11.13.
 */
public class ShoppingProviderContract {

    public static final String AUTHORITY = "net.sledzdev.shoppinglist.provider";

    public static final Uri LIST_URI = Uri.parse("content://" + AUTHORITY + "/lists");
    public static final Uri ITEMS_URI = Uri.parse("content://" + AUTHORITY + "/items");

    public static final String LISTS_TYPE =
            "vnd.android.cursor.dir/vnd.net.sledzdev.shoppinglist.provider.lists";
    public static final String LIST_ROW_TYPE =
            "vnd.android.cursor.item/vnd.net.sledzdev.shoppinglist.provider.lists";

    public static final String ITEMS_TYPE =
            "vnd.android.cursor.dir/vnd.net.sledzdev.shoppinglist.provider.items";
    public static final String ITEMS_ROW_TYPE =
            "vnd.android.cursor.item/vnd.net.sledzdev.shoppinglist.provider.items";
}
