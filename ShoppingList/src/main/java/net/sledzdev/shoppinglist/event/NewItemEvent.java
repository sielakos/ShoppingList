package net.sledzdev.shoppinglist.event;

import net.sledzdev.shoppinglist.adapter.ItemAdapter;
import net.sledzdev.shoppinglist.manager.ContentManager;
import net.sledzdev.shoppinglist.model.ShoppingList;

/**
 * Created by mariusz on 03.02.14.
 */
public class NewItemEvent {

    public ItemAdapter adapter;
    public ShoppingList list;
    public ContentManager contentManager;

    public NewItemEvent(ItemAdapter adapter, ShoppingList list, ContentManager contentManager) {
        this.adapter = adapter;
        this.list = list;
        this.contentManager = contentManager;
    }
}
