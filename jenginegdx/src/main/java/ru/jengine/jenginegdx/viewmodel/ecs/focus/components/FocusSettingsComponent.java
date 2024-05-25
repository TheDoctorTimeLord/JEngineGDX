package ru.jengine.jenginegdx.viewmodel.ecs.focus.components;

import com.artemis.PooledComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.CanBeFilling;
import ru.jengine.jenginegdx.viewmodel.ecs.ExternalAddable;
import ru.jengine.jenginegdx.viewmodel.ecs.focus.FocusLoseHandler;

@ExternalAddable("focusSettings")
public class FocusSettingsComponent extends PooledComponent implements CanBeFilling<FocusSettingsComponent> {
    private FocusLoseHandler focusLoseHandler;

    public FocusSettingsComponent focusLoseHandler(FocusLoseHandler focusLoseHandler) {
        this.focusLoseHandler = focusLoseHandler;
        return this;
    }

    public FocusLoseHandler getFocusLoseHandler() {
        return focusLoseHandler;
    }

    @Override
    protected void reset() {
        this.focusLoseHandler = null;
    }

    @Override
    public boolean fill(FocusSettingsComponent other) {
        this.focusLoseHandler = other.focusLoseHandler;
        return focusLoseHandler != null;
    }
}
