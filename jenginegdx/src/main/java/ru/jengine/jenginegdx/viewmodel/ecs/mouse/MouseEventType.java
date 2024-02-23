package ru.jengine.jenginegdx.viewmodel.ecs.mouse;

import ru.jengine.jenginegdx.Constants.InputEvents;

public enum MouseEventType {
    MOVE(InputEvents.MOUSE_MOVE),
    TOUCH_DOWN(InputEvents.MOUSE_TOUCH_DOWN),
    TOUCH_UP(InputEvents.MOUSE_TOUCH_UP),
    START_DRAGGING(InputEvents.MOUSE_START_DRAGGING),
    DRAGGING(InputEvents.MOUSE_DRAGGING),
    DRAGGED_TO(InputEvents.MOUSE_DRAGGED_TO);

    private final String userEventCode;

    MouseEventType(String userEventCode) {
        this.userEventCode = userEventCode;
    }

    public String getUserEventCode() {
        return userEventCode;
    }
}