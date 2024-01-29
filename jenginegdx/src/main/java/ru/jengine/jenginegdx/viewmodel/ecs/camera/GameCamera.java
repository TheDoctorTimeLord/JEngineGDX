package ru.jengine.jenginegdx.viewmodel.ecs.camera;

import com.badlogic.gdx.graphics.Camera;
import ru.jengine.beancontainer.annotations.Bean;

@Bean
public class GameCamera {
    private final Camera camera;

    public GameCamera(Camera camera) {
        this.camera = camera;
        camera.update();
    }

    public Camera getCamera() {
        return camera;
    }
}
