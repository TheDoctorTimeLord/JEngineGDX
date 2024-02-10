package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;

public class DraggingComponent extends PooledComponent {
    private Vector2 draggingOffset;

    @Override
    protected void reset() {
        draggingOffset = Vector2.Zero;
    }
}
