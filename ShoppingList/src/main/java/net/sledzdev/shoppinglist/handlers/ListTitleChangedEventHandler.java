package net.sledzdev.shoppinglist.handlers;

import com.google.common.eventbus.Subscribe;

import net.sledzdev.shoppinglist.event.EventBusFactory;
import net.sledzdev.shoppinglist.event.ListTitleChangedEvent;
import net.sledzdev.shoppinglist.event.UpdateListAdapterEvent;

/**
 * Created by Mariusz on 26.01.14.
 */
public class ListTitleChangedEventHandler {

    @Subscribe
    public void onListTitleChanged(ListTitleChangedEvent event) {
        event.list.name = event.newTitle;
        event.manager.save(event.list);
        EventBusFactory.getEventBus().post(new UpdateListAdapterEvent());
    }
}
