package net.sledzdev.shoppinglist.event;

import net.sledzdev.shoppinglist.adapter.ItemAdapter;
import net.sledzdev.shoppinglist.model.ShoppingItem;

/**
 * Created by mariusz on 05.02.14.
 */
public class ItemDialogOpenRequestEvent {

    public ItemAdapter adapter;
    public ShoppingItem item;

    public ItemDialogOpenRequestEvent(ItemAdapter adapter, ShoppingItem item) {
        this.adapter = adapter;
        this.item = item;
    }
}
