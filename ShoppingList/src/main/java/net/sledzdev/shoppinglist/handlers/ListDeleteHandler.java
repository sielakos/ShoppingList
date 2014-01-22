package net.sledzdev.shoppinglist.handlers;

import android.content.ContentResolver;

import com.google.common.eventbus.Subscribe;

import net.sledzdev.shoppinglist.ShoppingListsActivity;
import net.sledzdev.shoppinglist.event.ListDeleteEvent;

/**
 * Created by Mariusz on 22.01.14.
 */
public class ListDeleteHandler {

    private ShoppingListsActivity activity;

    public ListDeleteHandler(ShoppingListsActivity activity) {
        this.activity = activity;
    }

    @Subscribe
    public void onListDelete(ListDeleteEvent event) {
        event.adapter.deleteItem(event.listId);
        //TODO: finish this method
    }
}
