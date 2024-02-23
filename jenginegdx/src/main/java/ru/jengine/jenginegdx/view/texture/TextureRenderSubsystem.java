package ru.jengine.jenginegdx.view.texture;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.viewmodel.ecs.location.CoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.RotationComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.rendering.RenderSubsystem;

@Bean
public class TextureRenderSubsystem extends RenderSubsystem {
    private ComponentMapper<CoordinatesComponent> coordinatesComponentMapper;
    private ComponentMapper<TextureComponent> textureComponentMapper;
    private ComponentMapper<TextureBoundComponent> textureBoundComponentMapper;
    private ComponentMapper<RotationComponent> rotationComponentMapper;

    @Override
    public void render(int entityId, Batch batch) {
        if (!coordinatesComponentMapper.has(entityId) || !textureComponentMapper.has(entityId)) {
            return;
        }

        Vector3 coordinates = coordinatesComponentMapper.get(entityId).getCoordinates();
        TextureRegion region = textureComponentMapper.get(entityId).getRegion();

        float width = region.getRegionWidth();
        float height = region.getRegionHeight();
        if (textureBoundComponentMapper.has(entityId)) {
            TextureBoundComponent bound = textureBoundComponentMapper.get(entityId);
            width = bound.getWidth() == 0 ? region.getRegionWidth() : bound.getWidth();
            height = bound.getHeight() == 0 ? region.getRegionHeight() : bound.getHeight();
        }

        float rotation = rotationComponentMapper.has(entityId)
                ? rotationComponentMapper.get(entityId).getRotation()
                : RotationComponent.DEFAULT_ROTATION;

        //TODO разобраться с origin
        batch.draw(region, coordinates.x, coordinates.y, coordinates.x, coordinates.y, width, height, 1, 1, rotation);
    }
}
