package net.sledzdev.shoppinglist.event;

import net.sledzdev.shoppinglist.adapter.ItemAdapter;
import net.sledzdev.shoppinglist.model.ShoppingItem;

/**
 * Created by mariusz on 30.01.14.
 */
public class ItemPriceChangedEvent {

    public double newPrice;
    public ShoppingItem item;
    public ItemAdapter adapter;

    public ItemPriceChangedEvent(double newPrice, ShoppingItem item, ItemAdapter adapter) {
        this.newPrice = newPrice;
        this.item = item;
        this.adapter = adapter;
    }
}
