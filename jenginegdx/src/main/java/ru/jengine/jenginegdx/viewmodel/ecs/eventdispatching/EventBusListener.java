package ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching;

import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.systems.EventBus;

public interface EventBusListener {
    void registerListeners(EventBus eventBus);
    void unregisterListeners(EventBus eventBus);
}
