package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components;

import com.artemis.PooledComponent;

public class DraggingTrackingComponent extends PooledComponent {
    private float draggingX;
    private float draggingY;

    public void draggingPosition(float draggingX, float draggingY) {
        this.draggingX = draggingX;
        this.draggingY = draggingY;
    }

    public float getDraggingX() {
        return draggingX;
    }

    public float getDraggingY() {
        return draggingY;
    }

    @Override
    protected void reset() {
        draggingPosition(0, 0);
    }
}
