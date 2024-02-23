package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector3;

public class DraggingComponent extends PooledComponent {
    private float xOffsetToMouse;
    private float yOffsetToMouse;
    private Vector3 previousCoordinates;

    public DraggingComponent setOffsetToMouse(float xOffset, float yOffset) {
        this.xOffsetToMouse = xOffset;
        this.yOffsetToMouse = yOffset;

        return this;
    }

    public void setPreviousCoordinates(Vector3 previousCoordinates) {
        this.previousCoordinates = previousCoordinates;
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
        setOffsetToMouse(0, 0);
        setPreviousCoordinates(Vector3.Zero);
    }
}
