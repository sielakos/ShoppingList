package net.sledzdev.shoppinglist.model;

import net.sledzdev.shoppinglist.adapter.ElementWithId;

/**
 * Created by Mariusz on 10.12.13.
 */
public class ShoppingList implements ElementWithId {
    private long id;
    public String name;
    private boolean newList;

    ShoppingList(long id, String name) {
        this.id = id;
        this.name = name;
        newList = false;
    }

    /**
     * Creates list not present in database.
     *
     * @param name
     */
    public ShoppingList(String name) {
        this(-1, name);
        newList = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isNewList() {
        return newList;
    }
}
