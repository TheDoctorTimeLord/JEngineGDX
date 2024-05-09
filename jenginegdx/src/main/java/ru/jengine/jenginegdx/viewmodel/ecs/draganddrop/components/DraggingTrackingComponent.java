package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components;

import com.artemis.PooledComponent;

public class DraggingTrackingComponent extends PooledComponent {
    private float offsetX;
    private float offsetY;

    public void draggingOffset(float draggingX, float draggingY) {
        this.offsetX = draggingX;
        this.offsetY = draggingY;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    @Override
    protected void reset() {
        draggingOffset(0, 0);
    }
}
