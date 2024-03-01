package ru.jengine.jenginegdx.viewmodel.ecs.rendering.components;

import com.artemis.PooledComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.ExternalAddable;

@ExternalAddable
public class VisibleComponent extends PooledComponent {
    @Override
    protected void reset() { }
}
