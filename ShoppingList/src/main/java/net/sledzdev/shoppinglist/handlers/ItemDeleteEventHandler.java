package net.sledzdev.shoppinglist.handlers;

import android.widget.ListView;

import com.google.common.eventbus.Subscribe;

import net.sledzdev.shoppinglist.ShoppingListDetailActivity;
import net.sledzdev.shoppinglist.ShoppingListsActivity;
import net.sledzdev.shoppinglist.event.ItemDeleteEvent;
import net.sledzdev.shoppinglist.manager.ContentManager;

/**
 * Created by Mariusz on 26.01.14.
 */
public class ItemDeleteEventHandler {

    private ShoppingListsActivity shoppingListsActivity;
    private ShoppingListDetailActivity shoppingListDetailActivity;

    private ListView itemsListView;

    public ItemDeleteEventHandler(ShoppingListsActivity shoppingListsActivity) {
        this.shoppingListsActivity = shoppingListsActivity;
        if (shoppingListsActivity == null) {
            throw new IllegalArgumentException("Activity can't be null");
        }
    }

    public ItemDeleteEventHandler(ShoppingListDetailActivity shoppingListDetailActivity) {
        this.shoppingListDetailActivity = shoppingListDetailActivity;
        if (shoppingListDetailActivity == null) {
            throw new IllegalArgumentException("Activity can't be null");
        }
    }

    @Subscribe
    public void onItemDelete(ItemDeleteEvent event) {
        event.adapter.deleteItem(event.item.id);
        getContentManager().remove(event.item);

        //TODO: animation
    }

    private ContentManager getContentManager() {
        if (shoppingListsActivity != null) {
            return shoppingListsActivity.getContentManager();
        }

        return shoppingListDetailActivity.getContentManager();
    }


}
