package net.sledzdev.shoppinglist.model;

import net.sledzdev.shoppinglist.adapter.ElementWithId;

/**
 * Created by Mariusz on 10.12.13.
 */
public class ShoppingList implements ElementWithId {
    public String name;
    private long id;
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

    public void setNewList(boolean newList) {
        this.newList = newList;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", newList=" + newList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingList)) return false;

        ShoppingList list = (ShoppingList) o;

        if (id != list.id) return false;
        if (!name.equals(list.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }
}
