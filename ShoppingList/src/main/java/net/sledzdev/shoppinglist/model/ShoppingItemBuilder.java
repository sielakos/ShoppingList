package net.sledzdev.shoppinglist.model;

import com.google.common.base.Optional;

import net.sledzdev.shoppinglist.manager.ContentManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ShoppingItemBuilder {
    private static Set<ShoppingItem> createdItems = new HashSet<ShoppingItem>();
    private static Map<Long, ShoppingItem> itemsWithId = new HashMap<Long, ShoppingItem>();

    public void put(ShoppingItem item) {
        if (item.id != -1) {
            itemsWithId.put(item.id, item);
        }
    }

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

        if (checkIfNew()) {
            return createNewItem();
        }

        return changeExistingItem();
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

    protected boolean checkIfNew() {
        return id == -1 || !itemsWithId.containsKey(id);
    }

    protected ShoppingItem createNewItem() {
        ShoppingItem item = new ShoppingItem(id, name, price, list, id == -1);
        createdItems.add(item);
        put(item);
        return item;
    }

    protected ShoppingItem changeExistingItem() {
        ShoppingItem item = itemsWithId.get(id);
        item.list = list;
        item.name = name;
        item.price = price;
        return item;
    }

    protected boolean listNotSet() {
        return list == null && list_id < 0;
    }

    protected void setListFromId() throws InterruptedException, ExecutionException {
        Optional<ShoppingList> shoppingListOptional = getShoppingListOptional();
        if (!shoppingListOptional.isPresent()) {
            throw new IllegalArgumentException("shopping item must have list\n got: " + this);
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

    @Override
    public String toString() {
        return "ShoppingItemBuilder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", list=" + list +
                ", list_id=" + list_id +
                '}';
    }
}