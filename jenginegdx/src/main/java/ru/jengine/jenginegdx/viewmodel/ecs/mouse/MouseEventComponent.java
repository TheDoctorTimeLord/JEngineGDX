package ru.jengine.jenginegdx.viewmodel.ecs.mouse;

import ru.jengine.jenginegdx.viewmodel.ecs.input.UserInputComponent;

public class MouseEventComponent extends UserInputComponent {
    public float mouseX;
    public float mouseY;
    public MouseEventType eventType;

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
