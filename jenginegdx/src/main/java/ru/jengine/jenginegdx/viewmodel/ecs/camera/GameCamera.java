package ru.jengine.jenginegdx.viewmodel.ecs.camera;

import com.badlogic.gdx.graphics.Camera;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.container.JEngineGdxConfiguration;

@Bean
public class GameCamera {
    private final Camera camera;

    public GameCamera(JEngineGdxConfiguration configuration) {
        this.camera = configuration.getCamera();
        camera.update();
    }

    public Camera getCamera() {
        return camera;
    }
}
