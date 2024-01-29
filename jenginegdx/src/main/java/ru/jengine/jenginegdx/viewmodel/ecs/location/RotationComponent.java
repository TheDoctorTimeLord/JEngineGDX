package ru.jengine.jenginegdx.viewmodel.ecs.location;

import ru.jengine.jenginegdx.viewmodel.ecs.CanDirtyPooledComponent;

public class RotationComponent extends CanDirtyPooledComponent {
    public static final float DEFAULT_ROTATION = 0;

    public float rotation = DEFAULT_ROTATION;

    public RotationComponent rotation(float rotation) {
        this.rotation = rotation;
        dirty();
        return this;
    }

    @Override
    protected void reset() {
        rotation(DEFAULT_ROTATION);
    }
}
