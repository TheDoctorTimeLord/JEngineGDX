package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components;

import com.artemis.PooledComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.CanBeFilling;
import ru.jengine.jenginegdx.viewmodel.ecs.ExternalAddable;
import ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.DroppedHandler;

@ExternalAddable("droppedContainer")
public class DroppedContainerComponent extends PooledComponent implements CanBeFilling<DroppedContainerComponent> {
    private String targetDraggingType;
    private DroppedHandler droppedHandler;

    public DroppedContainerComponent droppedHandler(String targetDraggingType, DroppedHandler droppedHandler) {
        this.targetDraggingType = targetDraggingType;
        this.droppedHandler = droppedHandler;
        return this;
    }

    public String getTargetDraggingType() {
        return targetDraggingType;
    }

    public DroppedHandler getDroppedHandler() {
        return droppedHandler;
    }

    @Override
    protected void reset() {
        droppedHandler(null, null);
    }

    @Override
    public boolean fill(DroppedContainerComponent other) {
        this.targetDraggingType = other.targetDraggingType;
        this.droppedHandler = other.droppedHandler;
        return targetDraggingType != null && droppedHandler != null;
    }
}
