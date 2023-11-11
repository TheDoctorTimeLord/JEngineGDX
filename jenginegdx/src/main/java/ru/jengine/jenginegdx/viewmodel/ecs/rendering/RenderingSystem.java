package ru.jengine.jenginegdx.viewmodel.ecs.rendering;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.view.Renderer;
import ru.jengine.jenginegdx.view.RenderingBatches;
import ru.jengine.jenginegdx.viewmodel.ecs.SortedByZIteratingSystem;

@Bean
@All(RendererComponent.class)
public class RenderingSystem extends SortedByZIteratingSystem {
    private ComponentMapper<RendererComponent> rendererComponentMapper;
    private ComponentMapper<TextureComponent> textureComponentMapper;
    private final RenderingBatches renderingBatches;

    public RenderingSystem() {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);

        renderingBatches = new RenderingBatches(
                new SpriteBatch(),
                shapeRenderer
        );
    }

    @Override
    protected void begin() {
        ScreenUtils.clear(0, 0, 0, 1);

        renderingBatches.forSprite().begin();
        renderingBatches.forShape().begin();
    }

    @Override
    protected void process(int entityId) {
        Renderer renderer = rendererComponentMapper.get(entityId).renderer;
        if (renderer != null) {
            renderer.render(entityId, renderingBatches);
        }
    }

    @Override
    protected void end() {
        renderingBatches.forSprite().end();
        renderingBatches.forShape().end();
    }

    @Override
    protected void dispose() {
        renderingBatches.dispose();
    }
}
