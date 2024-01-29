package ru.jengine.jenginegdx.viewmodel.ecs.userevents;

import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.EventBusEvent;

public class UserEvent implements EventBusEvent {
    private final int targetEntityId;
    private final String userEvent;

    public UserEvent(int targetEntityId, String userEvent) {
        this.targetEntityId = targetEntityId;
        this.userEvent = userEvent;
    }

    public int getTargetEntityId() {
        return targetEntityId;
    }

    public String getUserEvent() {
        return userEvent;
    }
}
