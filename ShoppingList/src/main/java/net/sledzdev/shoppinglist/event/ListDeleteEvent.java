package net.sledzdev.shoppinglist.event;

import net.sledzdev.shoppinglist.adapter.ShoppingListsAdapter;

/**
 * Created by Mariusz on 22.01.14.
 */
public class ListDeleteEvent {

    public long listId;
    public ShoppingListsAdapter adapter;

    public ListDeleteEvent(long listId, ShoppingListsAdapter adapter) {
        this.listId = listId;
        this.adapter = adapter;
    }
}
