package ru.jengine.jenginegdx.viewmodel.ecs;

import com.artemis.PooledComponent;

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
