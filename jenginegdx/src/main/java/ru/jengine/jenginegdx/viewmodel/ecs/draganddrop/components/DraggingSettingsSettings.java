package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components;

import com.artemis.PooledComponent;

public class DraggingSettingsSettings extends PooledComponent {
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
}
