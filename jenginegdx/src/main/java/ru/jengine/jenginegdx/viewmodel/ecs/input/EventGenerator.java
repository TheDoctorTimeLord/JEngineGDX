package ru.jengine.jenginegdx.viewmodel.ecs.input;

import com.artemis.World;

public class EventGenerator {
    private final World world;

    public EventGenerator(World world) {
        this.world = world;
    }

    public <T extends UserInputComponent> T generate(Class<T> componentClass) {
        return world.createEntity().edit().create(componentClass);
    }
}
