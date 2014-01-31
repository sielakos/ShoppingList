package net.sledzdev.shoppinglist.event;

import net.sledzdev.shoppinglist.adapter.ItemAdapter;
import net.sledzdev.shoppinglist.model.ShoppingItem;

/**
 * Created by mariusz on 31.01.14.
 */
public class ItemCheckedChangedEvent {

    public ItemAdapter adapter;
    public ShoppingItem item;
    public boolean newChecked;

    public ItemCheckedChangedEvent(ItemAdapter adapter, ShoppingItem item, boolean newChecked) {
        this.adapter = adapter;
        this.item = item;
        this.newChecked = newChecked;
    }
}
