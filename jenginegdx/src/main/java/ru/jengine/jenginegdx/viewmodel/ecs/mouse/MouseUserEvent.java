package ru.jengine.jenginegdx.viewmodel.ecs.mouse;

import ru.jengine.jenginegdx.viewmodel.ecs.input.UserEvent;

public class MouseUserEvent extends UserEvent {
    private final float screenX;
    private final float screenY;

    public MouseUserEvent(int targetEntityId, String userEvent, float screenX, float screenY) {
        super(targetEntityId, userEvent);
        this.screenX = screenX;
        this.screenY = screenY;
    }

    public float getScreenX() {
        return screenX;
    }

    public float getScreenY() {
        return screenY;
    }
}
