package net.sledzdev.shoppinglist.handlers;

import com.google.common.eventbus.Subscribe;

import net.sledzdev.shoppinglist.event.ItemDeleteEvent;
import net.sledzdev.shoppinglist.manager.ContentManager;

/**
 * Created by Mariusz on 26.01.14.
 */
public class ItemDeleteEventHandler {

    private ContentManager manager;

    public ItemDeleteEventHandler(ContentManager manager) {
        if (manager == null) {
            throw new IllegalArgumentException("ContentManager can't be null");
        }
        this.manager = manager;
    }


    @Subscribe
    public void onItemDelete(ItemDeleteEvent event) {
        event.adapter.deleteItem(event.item.id);
        manager.remove(event.item);

        //TODO: animation
    }
}
