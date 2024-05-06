package ru.jengine.jenginegdx.viewmodel.ecs.location;

import com.badlogic.gdx.math.Vector3;
import ru.jengine.jenginegdx.viewmodel.ecs.CanBeFilling;
import ru.jengine.jenginegdx.viewmodel.ecs.utils.CanDirtyPooledComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.ExternalAddable;

@ExternalAddable("abs_coordinates")
public class AbsoluteCoordinatesComponent extends CanDirtyPooledComponent implements CanBeFilling<AbsoluteCoordinatesComponent> {
    private Vector3 coordinates = new Vector3(0,0,0);

    public AbsoluteCoordinatesComponent coordinates(Vector3 coordinates) {
        return coordinates(coordinates.x, coordinates.y, coordinates.z);
    }

    public AbsoluteCoordinatesComponent coordinates(float x, float y, float z) {
        this.coordinates = new Vector3(x, y, z);
        dirty();
        return this;
    }

    public float x(){
        return coordinates.x;
    }

    public float y(){
        return coordinates.y;
    }

    public float z(){
        return coordinates.z;
    }

    public Vector3 getCoordinates() {
        return coordinates;
    }

    @Override
    protected void reset() {
        this.coordinates = Vector3.Zero;
    }

    @Override
    public boolean fill(AbsoluteCoordinatesComponent other) {
        this.coordinates = other.coordinates;
        return coordinates != null;
    }
}
