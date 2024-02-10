package ru.jengine.jenginegdx.viewmodel.ecs.mouse;

import ru.jengine.jenginegdx.viewmodel.ecs.InputComponentMarker;
import ru.jengine.jenginegdx.viewmodel.ecs.input.InputComponent;

@InputComponentMarker
public class MouseEventComponent extends InputComponent {
    private float mouseX;
    private float mouseY;
    private MouseEventType eventType;

    public MouseEventComponent mouseX(float mouseX) {
        this.mouseX = mouseX;
        return this;
    }

    public MouseEventComponent mouseY(float mouseY) {
        this.mouseY = mouseY;
        return this;
    }

    public MouseEventComponent eventType(MouseEventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public float getMouseX() {
        return mouseX;
    }

    public float getMouseY() {
        return mouseY;
    }

    public MouseEventType getEventType() {
        return eventType;
    }

    @Override
    protected void reset() {
        mouseX = 0f;
        mouseY = 0f;
        eventType = null;
    }

    @Override
    public String toString() {
        return "Mouse [" + eventType.name() + ", x=" + mouseX +", y=" + mouseY + "]";
    }
}
