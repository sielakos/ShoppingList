package net.sledzdev.shoppinglist.event;

import com.google.common.base.Optional;
import com.google.common.eventbus.EventBus;

/**
 * Created by Mariusz on 08.12.13.
 */
public class EventBusFactory {

    static private Optional<EventBus> eventBus = Optional.absent();

    private EventBusFactory() {}

    static public EventBus getEventBus() {
        if (eventBus.isPresent()) {
           return eventBus.get();
        }

        eventBus = Optional.of(new EventBus());
        return eventBus.get();
    }

}
