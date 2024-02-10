package ru.jengine.jenginegdx.viewmodel.ecs.location;

import com.badlogic.gdx.math.Vector3;
import ru.jengine.jenginegdx.viewmodel.ecs.CanDirtyPooledComponent;

public class CoordinatesComponent extends CanDirtyPooledComponent {
    private Vector3 coordinates;

    public CoordinatesComponent coordinates(Vector3 coordinates) {
        this.coordinates = coordinates;
        dirty();
        return this;
    }

    public Vector3 getCoordinates() {
        return coordinates;
    }

    @Override
    protected void reset() {
        coordinates(Vector3.Zero);
    }
}
