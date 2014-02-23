package net.sledzdev.shoppinglist;

import com.google.common.eventbus.EventBus;

import net.sledzdev.shoppinglist.event.EventBusFactory;
import net.sledzdev.shoppinglist.handlers.ClearListEventHandler;
import net.sledzdev.shoppinglist.handlers.ItemChangedEventHandler;
import net.sledzdev.shoppinglist.handlers.ItemCheckedChangeEventHandler;
import net.sledzdev.shoppinglist.handlers.ItemDialogOpenRequestEventHandler;
import net.sledzdev.shoppinglist.handlers.ListTitleChangedEventHandler;
import net.sledzdev.shoppinglist.handlers.NewItemEventHandler;

import java.util.HashMap;
import java.util.Map;

public class DetailFragmentHandlersRegister {
    private final ShoppingListDetailFragment shoppingListDetailFragment;
    private RegisterManager registerManager;

    public DetailFragmentHandlersRegister(ShoppingListDetailFragment shoppingListDetailFragment) {
        this.shoppingListDetailFragment = shoppingListDetailFragment;
        registerManager = new RegisterManager(EventBusFactory.getEventBus());
    }

    void registerEventHandlers() {
        registerManager.register("list-title-handler", new ListTitleChangedEventHandler());
        registerManager.register("item-checked-handler", new ItemCheckedChangeEventHandler(shoppingListDetailFragment.getManager()));
        registerManager.register("clear-list-handler", new ClearListEventHandler(shoppingListDetailFragment.getActivity()));
        registerManager.register("new-item-handler", new NewItemEventHandler(shoppingListDetailFragment.getActivity()));
        registerManager.register("item-dialog-handler", new ItemDialogOpenRequestEventHandler(shoppingListDetailFragment.getActivity()));
        registerManager.register("item-changed-handler", new ItemChangedEventHandler(shoppingListDetailFragment));
    }
}

class RegisterManager {
    private static Map<String, Object> mapping = new HashMap<String, Object>();

    private EventBus eventBus;

    public RegisterManager(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void register(String name, Object handler) {
        if (mapping.containsKey(name)) {
            eventBus.unregister(mapping.get(name));
        }
        eventBus.register(handler);
        mapping.put(name, handler);
    }
}