package ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components;

import com.badlogic.gdx.math.Vector3;
import ru.jengine.jenginegdx.viewmodel.ecs.CanDirtyPooledComponent;

public class CoordinatesComponent extends CanDirtyPooledComponent {
    private Vector3 coordinates;

    public CoordinatesComponent coordinates(float x, float y, float z) {
        this.coordinates = new Vector3(x, y, z);
        dirty();
        return this;
    }

    public Vector3 getCoordinates() {
        return coordinates;
    }

    public float x() {
        return coordinates.x;
    }

    public float y() {
        return coordinates.y;
    }

    public float z() {
        return coordinates.z;
    }

    @Override
    protected void reset() {
        this.coordinates = Vector3.Zero;
    }
}
