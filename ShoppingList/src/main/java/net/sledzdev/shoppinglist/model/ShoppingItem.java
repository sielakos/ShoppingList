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
    public boolean newItem;

    public ShoppingItem(long id, String name, double price, ShoppingList list, boolean newItem) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.list = list;
        this.newItem = newItem;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ShoppingItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", list=" + list +
                ", price=" + price +
                ", newItem=" + newItem +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingItem)) return false;

        ShoppingItem item = (ShoppingItem) o;

        if (id != item.id) return false;
        if (Double.compare(item.price, price) != 0) return false;
        if (list != null ? !list.equals(item.list) : item.list != null) return false;
        if (!name.equals(item.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + (list != null ? list.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
