package ru.jengine.jenginegdx.viewmodel.ecs.input;

import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.EventBusEvent;

public class UserEvent implements EventBusEvent {
    private final int targetEntityId;
    private final String event;

    public UserEvent(int targetEntityId, String event) {
        this.targetEntityId = targetEntityId;
        this.event = event;
    }

    public int getTargetEntityId() {
        return targetEntityId;
    }

    public String getEvent() {
        return event;
    }
}
