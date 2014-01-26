package net.sledzdev.shoppinglist.event;

import net.sledzdev.shoppinglist.manager.ContentManager;
import net.sledzdev.shoppinglist.model.ShoppingList;

/**
 * Created by Mariusz on 26.01.14.
 */
public class ListTitleChangedEvent {
    public ContentManager manager;
    public ShoppingList list;
    public String newTitle;

    public ListTitleChangedEvent(ContentManager manager, ShoppingList list, String newTitle) {
        this.manager = manager;
        this.list = list;
        this.newTitle = newTitle;
    }
}
