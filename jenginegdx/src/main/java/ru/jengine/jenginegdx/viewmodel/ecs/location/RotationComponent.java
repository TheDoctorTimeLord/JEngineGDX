package ru.jengine.jenginegdx.viewmodel.ecs.location;

import ru.jengine.jenginegdx.viewmodel.ecs.CanDirtyPooledComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.ExternalAddable;
import ru.jengine.jenginegdx.viewmodel.ecs.cleaning.CanBeDirty;

@CanBeDirty
@ExternalAddable
public class RotationComponent extends CanDirtyPooledComponent {
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
}
