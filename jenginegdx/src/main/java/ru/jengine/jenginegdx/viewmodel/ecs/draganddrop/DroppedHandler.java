package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop;

import ru.jengine.jenginegdx.viewmodel.ecs.worldholder.WorldHolder;

public abstract class DroppedHandler {
    protected WorldHolder worldHolder;

    public void setWorldHolder(WorldHolder worldHolder) {
        this.worldHolder = worldHolder;

        worldHolder.getWorld().inject(this);
    }

    public abstract void handle(int target, int container, float droppedX, float droppedY, float xOffsetToMouse,
                                float yOffsetToMouse, String draggingType);
}
