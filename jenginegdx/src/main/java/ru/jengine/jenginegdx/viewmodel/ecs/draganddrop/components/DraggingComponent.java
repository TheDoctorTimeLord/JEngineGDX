package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class DraggingComponent extends PooledComponent {
    private float xDraggingOffset;
    private float yDraggingOffset;
    private float xOffsetToMouse;
    private float yOffsetToMouse;
    private Vector3 previousCoordinates;

    public DraggingComponent draggingOffset(Vector2 offset) {
        this.xDraggingOffset = offset.x;
        this.yDraggingOffset = offset.y;

        return this;
    }

    public DraggingComponent offsetToMouse(float xOffset, float yOffset) {
        this.xOffsetToMouse = xOffset;
        this.yOffsetToMouse = yOffset;

        return this;
    }

    public void previousCoordinates(Vector3 previousCoordinates) {
        this.previousCoordinates = previousCoordinates;
    }

    public float getXDraggingOffset() {
        return xDraggingOffset;
    }

    public float getYDraggingOffset() {
        return yDraggingOffset;
    }

    public float getXOffsetToMouse() {
        return xOffsetToMouse;
    }

    public float getYOffsetToMouse() {
        return yOffsetToMouse;
    }

    public Vector3 getPreviousCoordinate() {
        return previousCoordinates;
    }

    @Override
    protected void reset() {
        offsetToMouse(0, 0);
        previousCoordinates(Vector3.Zero);
    }
}
