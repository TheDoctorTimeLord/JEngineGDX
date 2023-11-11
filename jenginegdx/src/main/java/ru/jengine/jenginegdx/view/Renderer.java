package ru.jengine.jenginegdx.view;

import ru.jengine.beancontainer.annotations.PostConstruct;
import ru.jengine.jenginegdx.container.wrappers.WorldWrapper;

public abstract class Renderer {
    @PostConstruct
    private void setWorld(WorldWrapper world) {
        world.getWorld().inject(this);
    }

    public abstract void render(int entityId, RenderingBatches renderingBatches);
}
