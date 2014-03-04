package net.sledzdev.shoppinglist;

import com.google.common.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mariusz on 24.02.14.
 */
class SimpleRegisterManager implements RegisterManager {
    private Map<String, Object> mapping;

    private EventBus eventBus;

    public SimpleRegisterManager(EventBus eventBus) {
        this.eventBus = eventBus;
        mapping = new HashMap<String, Object>();
    }

    @Override
    public void register(String name, Object handler) {
        if (mapping.containsKey(name)) {
            eventBus.unregister(mapping.get(name));
        }
        eventBus.register(handler);
        mapping.put(name, handler);
    }

    @Override
    public void unregisterAll() {
        for (Object handler : mapping.values()) {
            eventBus.unregister(handler);
        }
        mapping.clear();
    }
}