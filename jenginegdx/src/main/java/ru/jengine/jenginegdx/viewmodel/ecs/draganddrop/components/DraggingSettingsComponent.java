package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components;

import com.artemis.PooledComponent;
import ru.jengine.jenginegdx.Constants.Linking;
import ru.jengine.jenginegdx.viewmodel.ecs.CanBeFilling;
import ru.jengine.jenginegdx.viewmodel.ecs.ExternalAddable;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.EntityLink;

@ExternalAddable("draggingSettings")
public class DraggingSettingsComponent extends PooledComponent implements CanBeFilling<DraggingSettingsComponent> {
    private String draggableType;
    @EntityLink private int draggingEntity = Linking.LINK_TO_NULL;

    public DraggingSettingsComponent draggableType(String draggableType) {
        this.draggableType = draggableType;
        return this;
    }

    public DraggingSettingsComponent draggingEntity(int target) {
        this.draggingEntity = target;
        return this;
    }

    public String getDraggableType() {
        return draggableType;
    }

    public int getDraggingEntity() {
        return draggingEntity;
    }

    @Override
    protected void reset() {
        this.draggableType = null;
    }

    @Override
    public boolean fill(DraggingSettingsComponent other) {
        this.draggableType = other.draggableType;
        this.draggingEntity = other.draggingEntity;
        return draggableType != null && draggingEntity >= Linking.LINK_TO_NULL;
    }
}
