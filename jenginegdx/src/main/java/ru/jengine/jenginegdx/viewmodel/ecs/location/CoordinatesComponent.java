package ru.jengine.jenginegdx.viewmodel.ecs.location;

import com.badlogic.gdx.math.Vector3;
import ru.jengine.jenginegdx.viewmodel.ecs.CanDirtyPooledComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.ExternalAddable;
import ru.jengine.jenginegdx.viewmodel.ecs.cleaning.CanBeDirty;

@CanBeDirty
@ExternalAddable
public class CoordinatesComponent extends CanDirtyPooledComponent {
    private Vector3 coordinates;

    public CoordinatesComponent coordinates(Vector3 coordinates) {
        return coordinates(coordinates.x, coordinates.y, coordinates.z);
    }

    public CoordinatesComponent coordinates(float x, float y, float z) {
        this.coordinates = new Vector3(x, y, z);
        dirty();
        return this;
    }

    public Vector3 getCoordinates() {
        return coordinates;
    }

    @Override
    protected void reset() {
        this.coordinates = Vector3.Zero;
    }
}
