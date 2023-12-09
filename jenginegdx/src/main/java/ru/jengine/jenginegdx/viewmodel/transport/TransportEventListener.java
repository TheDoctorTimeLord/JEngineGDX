package ru.jengine.jenginegdx.viewmodel.transport;

import ru.jengine.jenginegdx.viewmodel.transport.model.ModelTransport;
import ru.jengine.utils.HierarchyWalkingUtils;

public interface TransportEventListener<E extends TransportedEvent> {
    void handleListenedEvent(E event);
}
