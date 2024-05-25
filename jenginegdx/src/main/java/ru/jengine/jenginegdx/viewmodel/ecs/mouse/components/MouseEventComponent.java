package ru.jengine.jenginegdx.viewmodel.ecs.mouse.components;

import ru.jengine.jenginegdx.viewmodel.ecs.cleaning.CleanableComponentMarker;
import ru.jengine.jenginegdx.viewmodel.ecs.input.InputWithCoordinate;
import ru.jengine.jenginegdx.viewmodel.ecs.input.components.InputComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.MouseEventType;

@CleanableComponentMarker
public class MouseEventComponent extends InputComponent implements InputWithCoordinate {
    private float mouseX;
    private float mouseY;
    private MouseEventType eventType;

    public MouseEventComponent mousePosition(float mouseX, float mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        return this;
    }

    public MouseEventComponent eventType(MouseEventType eventType) {
        this.eventType = eventType;
        return this;
    }

    @Override
    public float getX() {
        return mouseX;
    }

    @Override
    public float getY() {
        return mouseY;
    }

    public MouseEventType getEventType() {
        return eventType;
    }

    @Override
    public boolean isInteractionEvent() {
        return eventType != null && eventType.isInteractionEvent();
    }

    @Override
    public String toEventCode() {
        return eventType.getUserEventCode();
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
