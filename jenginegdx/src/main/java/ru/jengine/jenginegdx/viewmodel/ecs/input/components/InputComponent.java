package ru.jengine.jenginegdx.viewmodel.ecs.input.components;

import com.artemis.PooledComponent;

public abstract class InputComponent extends PooledComponent {
    public abstract boolean isInteractionEvent();
    public abstract String toEventCode();
}
