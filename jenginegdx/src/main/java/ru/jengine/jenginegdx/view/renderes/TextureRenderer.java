package ru.jengine.jenginegdx.view.renderes;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.view.Renderer;
import ru.jengine.jenginegdx.viewmodel.ecs.location.CoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.rendering.TextureComponent;

@Bean
public class TextureRenderer extends Renderer {
    private ComponentMapper<CoordinatesComponent> coordinatesComponentMapper;
    private ComponentMapper<TextureComponent> textureComponentMapper;

    @Override
    public void render(int entityId, Batch batch) {
        if (!coordinatesComponentMapper.has(entityId) || !textureComponentMapper.has(entityId)) {
            return;
        }

        Texture texture = textureComponentMapper.get(entityId).texture;
        Vector3 coords = coordinatesComponentMapper.get(entityId).coordinates;

        batch.draw(texture, coords.x, coords.y);
    }
}
