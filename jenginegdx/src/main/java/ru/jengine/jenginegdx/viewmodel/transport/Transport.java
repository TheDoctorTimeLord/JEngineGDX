package ru.jengine.jenginegdx.viewmodel.transport;

public interface Transport<E extends TransportedEvent, L extends TransportEventListener<?>> {
    void send(E event);

    void setExternalEventListener(L listener);
}
