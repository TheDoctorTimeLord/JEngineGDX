package ru.jengine.jenginegdx.viewmodel.camera;

import com.badlogic.gdx.graphics.Camera;
import ru.jengine.beancontainer.annotations.Api;
import ru.jengine.beancontainer.annotations.Api.ApiElement;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.container.JEngineGdxConfiguration;

@Bean
public class GameCamera {
    private final Camera camera;

    @Api(@ApiElement(index = 0, message = "Add JEngineGdxConfiguration in JEngine.configureContainer(DefaultContainerConfigurationBuilder)"))
    public GameCamera(JEngineGdxConfiguration configuration) {
        this.camera = configuration.getCamera();
        camera.update();
    }

    public Camera getCamera() {
        return camera;
    }
}
