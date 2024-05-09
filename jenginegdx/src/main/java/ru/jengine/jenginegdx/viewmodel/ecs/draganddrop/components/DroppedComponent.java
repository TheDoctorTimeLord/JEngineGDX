package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components;

import com.artemis.PooledComponent;

public class DroppedComponent extends PooledComponent {
    private float xDropped;
    private float yDropped;
    private float zActual;

    public DroppedComponent droppedTo(float x, float y, float actualZ) {
        this.xDropped = x;
        this.yDropped = y;
        this.zActual = actualZ;

        return this;
    }

    public float getXDropped() {
        return xDropped;
    }

    public float getYDropped() {
        return yDropped;
    }

    public float getZActual() {
        return zActual;
    }

    @Override
    protected void reset() {
        droppedTo(0, 0, 0);
    }
}
