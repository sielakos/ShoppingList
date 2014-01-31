package net.sledzdev.shoppinglist.handlers;

import android.content.res.Resources;
import android.util.Log;

import com.google.common.eventbus.Subscribe;

import net.sledzdev.shoppinglist.R;
import net.sledzdev.shoppinglist.event.NewListEvent;
import net.sledzdev.shoppinglist.manager.ContentManager;
import net.sledzdev.shoppinglist.model.ShoppingList;

/**
 * Created by mariusz on 31.01.14.
 */
public class NewListEventHandler {

    private ContentManager contentManager;
    private Resources resources;

    public NewListEventHandler(ContentManager contentManager, Resources resources) {
        this.contentManager = contentManager;
        this.resources = resources;
    }

    @Subscribe
    public void onNewListEvent(NewListEvent event) {
        ShoppingList list = new ShoppingList(resources.getString(R.string.new_list_name));
        event.adapter.addItem(list);
        contentManager.save(list);
    }
}
