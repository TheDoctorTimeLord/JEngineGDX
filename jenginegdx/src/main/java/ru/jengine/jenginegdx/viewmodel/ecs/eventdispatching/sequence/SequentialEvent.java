package ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.sequence;

import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.EventBusEvent;

public interface SequentialEvent extends EventBusEvent {
    String[] getSubEventNames();
}
