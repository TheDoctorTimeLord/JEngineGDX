package ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching;

public interface EventBusListener {
    void registerListeners(EventBus eventBus);
    void unregisterListeners(EventBus eventBus);
}
