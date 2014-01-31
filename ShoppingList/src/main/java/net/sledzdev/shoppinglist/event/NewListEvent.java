package net.sledzdev.shoppinglist.event;

import net.sledzdev.shoppinglist.adapter.ShoppingListsAdapter;

/**
 * Created by mariusz on 31.01.14.
 */
public class NewListEvent {

    public ShoppingListsAdapter adapter;

    public NewListEvent(ShoppingListsAdapter adapter) {
        this.adapter = adapter;
    }
}
