package net.sledzdev.shoppinglist.handlers;

import com.google.common.eventbus.Subscribe;

import net.sledzdev.shoppinglist.event.ItemNameChangedEvent;
import net.sledzdev.shoppinglist.manager.ContentManager;

/**
 * Created by Mariusz on 27.01.14.
 */
public class ItemNameChangedEventHandler {

    private ContentManager contentManager;

    public ItemNameChangedEventHandler(ContentManager contentManager) {
        this.contentManager = contentManager;
    }

    @Subscribe
    public void onItemNameChanged(ItemNameChangedEvent event) {
       event.item.name = event.newName;
       contentManager.save(event.item);
    }
}
