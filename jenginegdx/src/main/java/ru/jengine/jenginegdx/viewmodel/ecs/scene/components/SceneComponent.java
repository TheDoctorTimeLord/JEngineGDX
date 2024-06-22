package ru.jengine.jenginegdx.viewmodel.ecs.scene.components;

import com.artemis.PooledComponent;

public class SceneComponent extends PooledComponent {
    int sceneId;

    public void setSceneId(int id) {
        sceneId = id;
    }

    public int getSceneId() {
        return sceneId;
    }

    @Override
    protected void reset() {
        sceneId = 0;
    }
}
