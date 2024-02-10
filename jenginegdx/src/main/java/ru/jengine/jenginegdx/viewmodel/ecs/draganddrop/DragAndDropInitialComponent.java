package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop;

import com.artemis.PooledComponent;
import com.badlogic.gdx.math.Vector2;

public class DragAndDropInitialComponent extends PooledComponent {
    public Vector2 mouseOffset;

    @Override
    protected void reset() {
        mouseOffset = Vector2.Zero;
    }
}
