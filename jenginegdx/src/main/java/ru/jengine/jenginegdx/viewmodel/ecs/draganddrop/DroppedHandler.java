package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop;

public abstract class DroppedHandler {
    public abstract void handle(int target, int container, float droppedX, float droppedY, float xOffsetToMouse,
                                float yOffsetToMouse, String draggingType);
}
