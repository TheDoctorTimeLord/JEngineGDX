package ru.jengine.jenginegdx.viewmodel.ecs.rendering.systems;

import com.artemis.annotations.All;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.beancontainer.annotations.PostConstruct;
import ru.jengine.jenginegdx.container.JEngineGdxConfiguration;
import ru.jengine.jenginegdx.viewmodel.camera.GameCamera;
import ru.jengine.jenginegdx.viewmodel.ecs.rendering.RenderSubsystem;
import ru.jengine.jenginegdx.viewmodel.ecs.rendering.components.VisibleComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.utils.SortedByZIteratingSystem;

import java.util.List;

@Bean
@Order(65536)
@All(VisibleComponent.class)
public class RenderingSystem extends SortedByZIteratingSystem {
    private final Batch batch;
    private final GameCamera gameCamera;
    private final List<RenderSubsystem> subsystems;

    public RenderingSystem(JEngineGdxConfiguration configuration, GameCamera gameCamera, List<RenderSubsystem> subsystems) {
        this.gameCamera = gameCamera;
        this.batch = configuration.getBatch();
        this.subsystems = subsystems;
    }

    @PostConstruct
    private void injectInSubsystems() {
        for (RenderSubsystem subsystem : subsystems) {
            world.inject(subsystem);
        }
    }

    @Override
    protected void begin() {
        ScreenUtils.clear(0, 0, 0, 1);

        batch.setProjectionMatrix(gameCamera.getCamera().combined);
        batch.begin();
    }

    @Override
    protected void process(int entityId) {
        for (RenderSubsystem subsystem : subsystems) {
            subsystem.render(entityId, batch);
        }
    }

    @Override
    protected void end() {
        batch.end();
    }

    @Override
    protected void dispose() {
        batch.dispose();
    }
}
