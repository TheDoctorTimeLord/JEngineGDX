package ru.jengine.jenginegdx.viewmodel.ecs.transform;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector3;

public class TransformComponent extends PooledComponent {
    private static final Vector3 ONE = new Vector3(1f, 1f, 1f);

    public Vector3 coordinates;
    public Vector3 rotates;
    public Vector3 scales;

    public TransformComponent coordinates(Vector3 coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public TransformComponent rotates(Vector3 rotates) {
        this.rotates = rotates;
        return this;
    }

    public TransformComponent scales(Vector3 scales) {
        this.scales = scales;
        return this;
    }

    @Override
    protected void reset() {
        coordinates = Vector3.Zero;
        rotates = Vector3.Zero;
        scales = ONE;
    }
}
