package ru.jengine.jenginegdx.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.jengine.beancontainer.annotations.PostConstruct;
import ru.jengine.jenginegdx.viewmodel.ecs.WorldHolder;

public abstract class Renderer {
    @PostConstruct
    private void setWorld(WorldHolder world) {
        world.getWorld().inject(this);
    }

    public abstract void render(int entityId, Batch batch);
}
