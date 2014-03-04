package net.sledzdev.shoppinglist;

import net.sledzdev.shoppinglist.event.EventBusFactory;
import net.sledzdev.shoppinglist.handlers.ClearListEventHandler;
import net.sledzdev.shoppinglist.handlers.ItemChangedEventHandler;
import net.sledzdev.shoppinglist.handlers.ItemCheckedChangeEventHandler;
import net.sledzdev.shoppinglist.handlers.ItemDeleteEventHandler;
import net.sledzdev.shoppinglist.handlers.ItemDialogOpenRequestEventHandler;
import net.sledzdev.shoppinglist.handlers.ListTitleChangedEventHandler;
import net.sledzdev.shoppinglist.handlers.NewItemEventHandler;

public class DetailFragmentHandlersRegister {
    private final ShoppingListDetailFragment shoppingListDetailFragment;
    private RegisterManager registerManager;

    public DetailFragmentHandlersRegister(ShoppingListDetailFragment shoppingListDetailFragment) {
        this.shoppingListDetailFragment = shoppingListDetailFragment;
        registerManager = new SimpleRegisterManager(EventBusFactory.getEventBus());
    }

    void registerEventHandlers() {
        registerManager.register("list-title-handler", new ListTitleChangedEventHandler());
        registerManager.register("item-checked-handler", new ItemCheckedChangeEventHandler(shoppingListDetailFragment.getManager()));
        registerManager.register("clear-list-handler", new ClearListEventHandler(shoppingListDetailFragment.getActivity()));
        registerManager.register("new-item-handler", new NewItemEventHandler(shoppingListDetailFragment.getActivity()));
        registerManager.register("item-dialog-handler", new ItemDialogOpenRequestEventHandler(shoppingListDetailFragment.getActivity()));
        registerManager.register("item-changed-handler", new ItemChangedEventHandler(shoppingListDetailFragment));
        registerManager.register("item-delete-handler", new ItemDeleteEventHandler(shoppingListDetailFragment.getManager()));
        ItemDeleteEventHandler itemDeleteEventHandler = new ItemDeleteEventHandler(shoppingListDetailFragment.getManager());
        registerManager.register("item-delete-handler", itemDeleteEventHandler);
    }

    void unregisterAll() {
        registerManager.unregisterAll();
    }
}

