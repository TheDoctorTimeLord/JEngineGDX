package ru.jengine.jenginegdx.viewmodel.ecs.bounds;

import com.artemis.PooledComponent;
import ru.jengine.jenginegdx.utils.figures.PositionedFigure;

public class GlobalBoundComponent extends PooledComponent {
    public PositionedFigure bound;

    @Override
    protected void reset() {
        bound = null;
    }
}
