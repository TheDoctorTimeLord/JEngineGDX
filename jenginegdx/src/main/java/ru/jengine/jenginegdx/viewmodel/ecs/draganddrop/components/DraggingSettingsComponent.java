package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components;

import com.artemis.PooledComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.CanBeFilling;
import ru.jengine.jenginegdx.viewmodel.ecs.ExternalAddable;

@ExternalAddable("draggingSettings")
public class DraggingSettingsComponent extends PooledComponent implements CanBeFilling<DraggingSettingsComponent> {
    private String draggableType;

    public void setDraggableType(String draggableType) {
        this.draggableType = draggableType;
    }

    public String getDraggableType() {
        return draggableType;
    }

    @Override
    protected void reset() {
        this.draggableType = null;
    }

    @Override
    public boolean fill(DraggingSettingsComponent other) {
        this.draggableType = other.draggableType;
        return draggableType != null;
    }
}
