package net.sledzdev.shoppinglist.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.common.base.Optional;

import net.sledzdev.shoppinglist.loader.ContentManager;

public class ShoppingItemBuilder {
    private static Map<Long, ShoppingItem> createdItems = new HashMap<Long, ShoppingItem>();

    private long id = -1;
    private String name;
    private double price = 0.0;
    private ShoppingList list;
    private long list_id = -1;

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
        preconditions();

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

    protected void preconditions() {
        if (listNotSet() || name == null) {
            throw new IllegalArgumentException("shopping list or name not set!");
        }

        if (list == null) {
            try {
                setListFromId();
            } catch (InterruptedException e) {
                throw new IllegalArgumentException(e.toString());
            } catch (ExecutionException e) {
                throw new IllegalArgumentException(e.toString());
            }
        }
    }

    protected boolean listNotSet() {
        return list == null && list_id < 0;
    }

    protected void setListFromId() throws InterruptedException, ExecutionException{
        Optional<ShoppingList> shoppingListOptional = getShoppingListOptional();
        if (!shoppingListOptional.isPresent()) {
            throw new IllegalArgumentException("shopping item must have list");
        }
        list = shoppingListOptional.orNull();
    }

    private Optional<ShoppingList> getShoppingListOptional() throws InterruptedException, ExecutionException {
        Optional<ShoppingList> shoppingListOptional;
        Optional<ContentManager> managerOptional = ContentManager.getExistingManager();

        if (managerOptional.isPresent()) {
            shoppingListOptional = managerOptional.get().getList(list_id).get();
        } else {
            shoppingListOptional = ShoppingListFactory.get(list_id);
        }
        return shoppingListOptional;
    }

}