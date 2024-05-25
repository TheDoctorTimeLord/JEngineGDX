package ru.jengine.jenginegdx.viewmodel.ecs.focus;

import ru.jengine.jenginegdx.viewmodel.ecs.input.components.InputComponent;

@FunctionalInterface
public interface FocusLoseHandler {
    boolean isFocusLost(int focusedId, InputComponent input);
}
