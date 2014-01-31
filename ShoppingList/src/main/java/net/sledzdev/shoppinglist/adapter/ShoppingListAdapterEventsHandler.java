package net.sledzdev.shoppinglist.adapter;

import com.google.common.eventbus.Subscribe;

import net.sledzdev.shoppinglist.event.NewListEvent;
import net.sledzdev.shoppinglist.event.NewListRequestEvent;
import net.sledzdev.shoppinglist.event.UpdateListAdapterEvent;

public class ShoppingListAdapterEventsHandler {
    private final ShoppingListsAdapter shoppingListsAdapter;

    public ShoppingListAdapterEventsHandler(ShoppingListsAdapter shoppingListsAdapter) {
        this.shoppingListsAdapter = shoppingListsAdapter;
    }

    @Subscribe
    public void onListTitleChanged(UpdateListAdapterEvent event) {
        shoppingListsAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onNewListRequest(NewListRequestEvent event) {
        shoppingListsAdapter.getEventBus().post(new NewListEvent(shoppingListsAdapter));
    }
}