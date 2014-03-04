package net.sledzdev.shoppinglist;

import net.sledzdev.shoppinglist.event.EventBusFactory;
import net.sledzdev.shoppinglist.handlers.ListDeleteEventHandler;
import net.sledzdev.shoppinglist.handlers.ListSelectedEventHandler;
import net.sledzdev.shoppinglist.handlers.NewListEventHandler;

public class ShoppingListActivityHandlersRegister {
    RegisterManager registerManager;
    ShoppingListsActivity activity;

    public ShoppingListActivityHandlersRegister(ShoppingListsActivity activity) {
        this.activity = activity;
        registerManager = new SimpleRegisterManager(EventBusFactory.getEventBus());
    }

    void registerHandlers() {
        ListSelectedEventHandler listSelectedEventHandler = new ListSelectedEventHandler(activity);
        registerManager.register("list-selected-handler", listSelectedEventHandler);

        ListDeleteEventHandler listDeleteHandler = new ListDeleteEventHandler(activity);
        registerManager.register("list-delete-handler", listDeleteHandler);

        NewListEventHandler newListEventHandler = new NewListEventHandler(activity);
        registerManager.register("new-list-handler", newListEventHandler);
    }

    void unregisterAll() {
        registerManager.unregisterAll();
    }
}