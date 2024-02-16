package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components;

import com.artemis.PooledComponent;

public class DraggingComponent extends PooledComponent {
    private float xOffsetToMouse;
    private float yOffsetToMouse;

    public void setOffsetToMouse(float xOffset, float yOffset) {
        this.xOffsetToMouse = xOffset;
        this.yOffsetToMouse = yOffset;
    }

    public float getXOffsetToMouse() {
        return xOffsetToMouse;
    }

    public float getYOffsetToMouse() {
        return yOffsetToMouse;
    }

    @Override
    protected void reset() {
        this.xOffsetToMouse = 0;
        this.yOffsetToMouse = 0;
    }
}
