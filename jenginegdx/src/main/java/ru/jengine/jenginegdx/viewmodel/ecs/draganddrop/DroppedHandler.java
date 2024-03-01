package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop;

import com.artemis.World;

public abstract class DroppedHandler {
    protected DroppedHandler(World world) {
        world.inject(this);
    }

    public abstract void handle(int target, int container, float droppedX, float droppedY, float xOffsetToMouse,
                                float yOffsetToMouse, String draggingType);
}
