package ru.jengine.jenginegdx.viewmodel.ecs.mouse;

import ru.jengine.jenginegdx.Constants.InputEvents;

public enum MouseEventType {
    MOVE(InputEvents.MOUSE_MOVE, false),
    TOUCH_DOWN(InputEvents.MOUSE_TOUCH_DOWN, true),
    TOUCH_UP(InputEvents.MOUSE_TOUCH_UP, true),
    START_DRAGGING(InputEvents.MOUSE_START_DRAGGING, true),
    DRAGGING(InputEvents.MOUSE_DRAGGING, false),
    DRAGGED_TO(InputEvents.MOUSE_DRAGGED_TO, true);

    private final String userEventCode;
    private final boolean isInformationEvent;

    MouseEventType(String userEventCode, boolean isInformationEvent) {
        this.userEventCode = userEventCode;
        this.isInformationEvent = isInformationEvent;
    }

    public String getUserEventCode() {
        return userEventCode;
    }

    public boolean isInteractionEvent() {
        return isInformationEvent;
    }
}