package ru.jengine.jenginegdx.viewmodel.ecs.rendering;

import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class RenderSubsystem {
    public abstract void render(int entityId, Batch batch);
}
