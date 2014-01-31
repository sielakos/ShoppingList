package net.sledzdev.shoppinglist.handlers;

import com.google.common.eventbus.Subscribe;

import net.sledzdev.shoppinglist.event.ItemPriceChangedEvent;
import net.sledzdev.shoppinglist.manager.ContentManager;

/**
 * Created by mariusz on 30.01.14.
 */
public class ItemPriceChangeEventHandler {

    private ContentManager contentManager;

    public ItemPriceChangeEventHandler(ContentManager contentManager) {
        this.contentManager = contentManager;
    }

    @Subscribe
    public void onItemPriceChanged(ItemPriceChangedEvent event) {
        event.item.price = event.newPrice;
        contentManager.save(event.item);
    }
}
