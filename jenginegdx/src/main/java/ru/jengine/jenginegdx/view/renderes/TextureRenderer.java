package ru.jengine.jenginegdx.view.renderes;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.view.Renderer;
import ru.jengine.jenginegdx.view.RenderingBatches;
import ru.jengine.jenginegdx.viewmodel.ecs.rendering.TextureComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.transform.TransformComponent;

@Bean
public class TextureRenderer extends Renderer {
    private ComponentMapper<TransformComponent> transformComponentMapper;
    private ComponentMapper<TextureComponent> textureComponentMapper;

    @Override
    public void render(int entityId, RenderingBatches renderingBatches) {
        if (!transformComponentMapper.has(entityId) || !textureComponentMapper.has(entityId)) {
            return;
        }

        Texture texture = textureComponentMapper.get(entityId).texture;
        Vector3 coords = transformComponentMapper.get(entityId).coordinates;

        renderingBatches.forSprite().draw(texture, coords.x, coords.y);
    }
}
