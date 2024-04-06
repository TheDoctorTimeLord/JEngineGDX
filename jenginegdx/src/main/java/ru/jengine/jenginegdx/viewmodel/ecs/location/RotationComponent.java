package ru.jengine.jenginegdx.viewmodel.ecs.location;

import ru.jengine.jenginegdx.viewmodel.ecs.CanBeFilling;
import ru.jengine.jenginegdx.viewmodel.ecs.CanDirtyPooledComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.ExternalAddable;

@ExternalAddable("rotation")
public class RotationComponent extends CanDirtyPooledComponent implements CanBeFilling<RotationComponent> {
    public static final float DEFAULT_ROTATION = 0;

    private float rotation = DEFAULT_ROTATION;

    public RotationComponent rotation(float rotation) {
        this.rotation = rotation;
        dirty();
        return this;
    }

    public float getRotation() {
        return rotation;
    }

    @Override
    protected void reset() {
        rotation(DEFAULT_ROTATION);
    }

    @Override
    public boolean fill(RotationComponent other) {
        this.rotation = other.rotation;
        return true;
    }
}
