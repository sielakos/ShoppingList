package net.sledzdev.shoppinglist.handlers;

import com.google.common.eventbus.Subscribe;

import net.sledzdev.shoppinglist.event.ItemCheckedChangedEvent;
import net.sledzdev.shoppinglist.manager.ContentManager;

/**
 * Created by mariusz on 31.01.14.
 */
public class ItemCheckedChangeEventHandler {

    private ContentManager contentManager;

    public ItemCheckedChangeEventHandler(ContentManager contentManager) {
        this.contentManager = contentManager;
    }

    @Subscribe
    public void onItemCheckedChanged(ItemCheckedChangedEvent event) {
        event.item.checked = event.newChecked;
        contentManager.save(event.item);
    }
}
