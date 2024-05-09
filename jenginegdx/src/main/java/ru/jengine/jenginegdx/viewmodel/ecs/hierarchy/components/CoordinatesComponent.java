package ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components;

import com.badlogic.gdx.math.Vector3;
import ru.jengine.jenginegdx.viewmodel.ecs.CanBeFilling;
import ru.jengine.jenginegdx.viewmodel.ecs.ExternalAddable;
import ru.jengine.jenginegdx.viewmodel.ecs.utils.CanDirtyPooledComponent;

@ExternalAddable("coordinates")
public class CoordinatesComponent extends CanDirtyPooledComponent implements CanBeFilling<CoordinatesComponent> {
    private Vector3 coordinates;

    public CoordinatesComponent coordinates(float x, float y, float z) {
        this.coordinates = new Vector3(x, y, z);
        dirty();
        return this;
    }

    public CoordinatesComponent coordinates(Vector3 coordinates) {
        return coordinates(coordinates.x, coordinates.y, coordinates.z);
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

    @Override
    public boolean fill(CoordinatesComponent other) {
        this.coordinates = other.coordinates;
        return coordinates != null;
    }
}
