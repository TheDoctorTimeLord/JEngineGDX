package ru.jengine.jenginegdx.viewmodel.ecs.bounds;

import ru.jengine.jenginegdx.utils.figures.FigureBound;
import ru.jengine.jenginegdx.viewmodel.ecs.CanDirtyPooledComponent;

public class BoundComponent extends CanDirtyPooledComponent {
    public FigureBound bound;

    public BoundComponent bound(FigureBound bound) {
        this.bound = bound;
        dirty();
        return this;
    }

    @Override
    protected void reset() {
        bound(null);
    }
}
