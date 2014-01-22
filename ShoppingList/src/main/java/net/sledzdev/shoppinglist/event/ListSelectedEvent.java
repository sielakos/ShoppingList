package net.sledzdev.shoppinglist.event;

/**
 * Created by Mariusz on 21.01.14.
 */
public class ListSelectedEvent {

    public long selectId;

    public ListSelectedEvent(long selectId) {
        this.selectId = selectId;
    }
}
