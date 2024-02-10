package ru.jengine.jenginegdx.viewmodel.ecs.rendering;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface RenderSubsystem {
    void render(int entityId, Batch batch);
}
