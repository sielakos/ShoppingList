package net.sledzdev.shoppinglist.model;

import java.util.HashMap;
import java.util.Map;

public class ShoppingItemBuilder {
    //TODO: test this class
    private static Map<Long, ShoppingItem> createdItems = new HashMap<Long, ShoppingItem>();

    private long id;
    private String name;
    private double price;
    private ShoppingList list;
    private long list_id;

    public ShoppingItemBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public ShoppingItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ShoppingItemBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public ShoppingItemBuilder setList(ShoppingList list) {
        this.list = list;
        return this;
    }

    public ShoppingItemBuilder setList_id(long list_id) {
        this.list_id = list_id;
        return this;
    }

    public ShoppingItem createShoppingItem() {
        //TODO: check if necessary field arte set

        if (list == null) {
            //TODO: get list from list_id if list not set
        }

        if (!createdItems.containsKey(id)) {
            ShoppingItem item = new ShoppingItem(id, name, price, list);
            createdItems.put(id, item);
            return item;
        }

        ShoppingItem item = createdItems.get(id);
        item.list = list;
        item.name = name;
        item.price = price;
        return item;
    }


}