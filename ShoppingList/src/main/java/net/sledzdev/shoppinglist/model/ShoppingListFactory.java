package net.sledzdev.shoppinglist.model;

import android.util.Log;

import com.google.common.base.Optional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mariusz on 22.12.13.
 */
public class ShoppingListFactory {
    static Map<Long, ShoppingList> lists = new HashMap<Long, ShoppingList>();

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

    public static void clearLists()  {
        lists.clear();
    }

    public static void putList(ShoppingList list) {
        ShoppingList prevList = lists.get(list.getId());

        if (prevList != null && prevList != list) {
            throw new IllegalArgumentException("shopping list already present!");
        }

        lists.put(list.getId(), list);
    }

    public static Optional<ShoppingList> get(long id) {
        return Optional.fromNullable(lists.get(id));
    }

}
