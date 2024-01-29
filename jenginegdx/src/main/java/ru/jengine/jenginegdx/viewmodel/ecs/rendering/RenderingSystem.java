package ru.jengine.jenginegdx.viewmodel.ecs.rendering;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.Constants.SystemOrder;
import ru.jengine.jenginegdx.view.Renderer;
import ru.jengine.jenginegdx.viewmodel.ecs.camera.GameCamera;
import ru.jengine.jenginegdx.viewmodel.ecs.SortedByZIteratingSystem;

@Bean
@Order(SystemOrder.RENDERING_SYSTEMS)
@All(RendererComponent.class)
public class RenderingSystem extends SortedByZIteratingSystem {
    private final Batch renderingBatch = new SpriteBatch(); //TODO вынести в параметры системы
    private final GameCamera gameCamera;
    private ComponentMapper<RendererComponent> rendererComponentMapper;

    public RenderingSystem(GameCamera gameCamera) {
        this.gameCamera = gameCamera;
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
    }

    @Override
    protected void begin() {
        ScreenUtils.clear(0, 0, 0, 1);

        renderingBatch.setProjectionMatrix(gameCamera.getCamera().combined);
        renderingBatch.begin();
    }

    @Override
    protected void process(int entityId) {
        Renderer renderer = rendererComponentMapper.get(entityId).renderer;
        if (renderer != null) {
            renderer.render(entityId, renderingBatch);
        }
    }

    @Override
    protected void end() {
        renderingBatch.end();
    }

    @Override
    protected void dispose() {
        renderingBatch.dispose();
    }
}
