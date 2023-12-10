package ru.jengine.jenginegdx.viewmodel.eventbus;

import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.eventqueue.event.Event;
import ru.jengine.eventqueue.eventpool.ComplexEventPoolQueueHandler;

@Bean
public class ClientEventPoolHandler extends ComplexEventPoolQueueHandler {
    private static final String EVENT_POOL_CODE = "clientEventBus";

    public ClientEventPoolHandler() {
        super(EVENT_POOL_CODE);
    }

    @Override
    public boolean isValid(Event event) {
        return event instanceof EventBusEvent;
    }
}
