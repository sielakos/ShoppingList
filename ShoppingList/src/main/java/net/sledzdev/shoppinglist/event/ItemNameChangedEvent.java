package net.sledzdev.shoppinglist.event;

import net.sledzdev.shoppinglist.adapter.ItemAdapter;
import net.sledzdev.shoppinglist.model.ShoppingItem;

/**
 * Created by Mariusz on 27.01.14.
 */
public class ItemNameChangedEvent {

    public ShoppingItem item;
    public ItemAdapter adapter;
    public String newName;

    public ItemNameChangedEvent(ShoppingItem item, ItemAdapter adapter, String newName) {
        this.item = item;
        this.adapter = adapter;
        this.newName = newName;
    }
}
