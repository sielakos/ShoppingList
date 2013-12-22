package net.sledzdev.shoppinglist.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mariusz on 22.12.13.
 */
public class ShoppingListFactory {
    //TODO: use factory with manager

    private static Map<Long, ShoppingList> lists  = new HashMap<Long, ShoppingList>();

    public static ShoppingList createShoppingList(long id, String name) {
        ShoppingList list = lists.get(id);
        if (list == null) {
            list = new ShoppingList(id, name);
            lists.put(id, list);
        }

        if (!list.name.equals(name)) {
            list.name = name;
        }

        return list;
    }

    public static void putList(ShoppingList list) {
        ShoppingList prevList = lists.get(list.getId());

        if (prevList != null && prevList != list) {
            throw new IllegalArgumentException("shopping list already present!");
        }

        lists.put(list.getId(), list);
    }

}
