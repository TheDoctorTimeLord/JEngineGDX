package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.events;

import com.badlogic.gdx.math.Vector2;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.EventBusEvent;

public class DroppedTo implements EventBusEvent {
    private final int droppedEntity;
    private final Vector2 droppedCoordinates;

    public DroppedTo(int droppedEntity, Vector2 droppedCoordinates) {
        this.droppedEntity = droppedEntity;
        this.droppedCoordinates = droppedCoordinates;
    }

    public DroppedTo(int droppedEntity, float droppedX, float droppedY) {
        this(droppedEntity, new Vector2(droppedX, droppedY));
    }

    public int getDroppedEntity() {
        return droppedEntity;
    }

    public Vector2 getDroppedCoordinates() {
        return droppedCoordinates;
    }
}
