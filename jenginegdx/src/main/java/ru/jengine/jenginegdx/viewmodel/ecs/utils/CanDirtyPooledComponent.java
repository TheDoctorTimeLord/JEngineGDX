package ru.jengine.jenginegdx.viewmodel.ecs.utils;

import com.artemis.PooledComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.cleaning.CanBeDirty;

@CanBeDirty
public abstract class CanDirtyPooledComponent extends PooledComponent {
    private boolean isDirty = true;

    public void dirty() {
        this.isDirty = true;
    }

    public void clear() {
        this.isDirty = false;
    }

    public boolean isDirty() {
        return isDirty;
    }

    @Override
    protected void reset() {}
}
