package net.sledzdev.shoppinglist.event;

import net.sledzdev.shoppinglist.adapter.ItemAdapter;
import net.sledzdev.shoppinglist.model.ShoppingItem;

/**
 * Created by Mariusz on 25.01.14.
 */
public class ItemDeleteEvent {

    public ShoppingItem item;
    public ItemAdapter adapter;

    public ItemDeleteEvent(ShoppingItem item, ItemAdapter adapter) {
        this.item = item;
        this.adapter = adapter;
    }
}
