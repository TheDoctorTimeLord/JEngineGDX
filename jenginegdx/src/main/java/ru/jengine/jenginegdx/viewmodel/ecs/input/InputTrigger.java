package ru.jengine.jenginegdx.viewmodel.ecs.input;

import com.badlogic.gdx.InputAdapter;

public abstract class InputTrigger extends InputAdapter {
    public abstract void attachReceivedUserInput(EventGenerator eventGenerator);
}
