package net.sledzdev.shoppinglist.model;

import net.sledzdev.shoppinglist.adapter.ElementWithId;

/**
 * Created by Mariusz on 22.12.13.
 */
public class ShoppingItem implements ElementWithId {
    public long id;
    public String name;
    public ShoppingList list;
    public double price;

    public ShoppingItem(long id, String name, double price, ShoppingList list) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.list = list;
    }

    @Override
    public long getId() {
        return id;
    }
}
