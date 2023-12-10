package ru.jengine.jenginegdx.viewmodel.transport.eventbus;

import ru.jengine.jenginegdx.viewmodel.eventbus.EventBusEvent;
import ru.jengine.jenginegdx.viewmodel.transport.viewmodel.ViewModelTransportEvent;

public class SendToModelEvent implements EventBusEvent {
    private final ViewModelTransportEvent eventForSending;

    public SendToModelEvent(ViewModelTransportEvent eventForSending)
    {
        this.eventForSending = eventForSending;
    }

    public ViewModelTransportEvent getEventForSending()
    {
        return eventForSending;
    }
}
