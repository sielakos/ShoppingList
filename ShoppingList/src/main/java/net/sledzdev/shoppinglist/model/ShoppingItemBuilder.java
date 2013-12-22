package net.sledzdev.shoppinglist.model;

public class ShoppingItemBuilder {
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
        return new ShoppingItem(id, name, price, list);
    }
}