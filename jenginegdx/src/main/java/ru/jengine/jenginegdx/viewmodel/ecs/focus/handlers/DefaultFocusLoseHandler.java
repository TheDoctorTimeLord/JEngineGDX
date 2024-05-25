package ru.jengine.jenginegdx.viewmodel.ecs.focus.handlers;

import ru.jengine.jenginegdx.viewmodel.ecs.focus.FocusLoseHandler;
import ru.jengine.jenginegdx.viewmodel.ecs.input.components.InputComponent;

public class DefaultFocusLoseHandler implements FocusLoseHandler {
    @Override
    public boolean isFocusLost(int focusedId, InputComponent input) {
        return true;
    }
}
