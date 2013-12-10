package net.sledzdev.shoppinglist.adapter;

/**
 * Created by Mariusz on 10.12.13.
 */
public class ShoppingList implements ElementWithId {
    public long id;
    public String name;

    public ShoppingList(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }
}
