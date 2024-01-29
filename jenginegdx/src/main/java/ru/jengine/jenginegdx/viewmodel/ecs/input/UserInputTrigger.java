package ru.jengine.jenginegdx.viewmodel.ecs.input;

import com.artemis.World;
import com.badlogic.gdx.InputAdapter;

public abstract class UserInputTrigger extends InputAdapter {
    public abstract void attachReceivedUserInput(EventGenerator eventGenerator);
}
