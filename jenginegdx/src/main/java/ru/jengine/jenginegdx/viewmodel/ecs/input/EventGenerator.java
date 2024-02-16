package ru.jengine.jenginegdx.viewmodel.ecs.input;

import com.artemis.World;
import ru.jengine.jenginegdx.viewmodel.ecs.input.components.InputComponent;

public class EventGenerator {
    private final World world;

    public EventGenerator(World world) {
        this.world = world;
    }

    public <T extends InputComponent> T generate(Class<T> componentClass) {
        return world.createEntity().edit().create(componentClass);
    }
}
