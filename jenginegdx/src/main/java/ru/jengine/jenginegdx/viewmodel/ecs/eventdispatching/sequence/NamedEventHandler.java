package ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.sequence;

import ru.jengine.utils.HierarchyWalkingUtils;

public interface NamedEventHandler<E extends SequentialEvent> {
    String[] getHandlingEventNames();
    HandlingResult handle(String eventName, E sourceEvent);

    default Class<?> getSourceEventType() {
        return HierarchyWalkingUtils.getGenericType(getClass(), NamedEventHandler.class, 0);
    }
}
