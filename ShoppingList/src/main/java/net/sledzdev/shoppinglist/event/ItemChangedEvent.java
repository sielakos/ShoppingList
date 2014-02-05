package net.sledzdev.shoppinglist.event;

/**
 * Created by mariusz on 05.02.14.
 */
public class ItemChangedEvent {

    public long itemId;
    public String name;
    public double price;

    public ItemChangedEvent(long itemId, String name, double price) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
    }
}
