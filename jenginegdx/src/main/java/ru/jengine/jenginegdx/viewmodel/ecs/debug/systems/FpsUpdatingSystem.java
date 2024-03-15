package ru.jengine.jenginegdx.viewmodel.ecs.debug.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.view.text.TextComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.debug.components.FpsRenderingComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.AbsoluteCoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.rendering.components.VisibleComponent;

@Bean
@Order(512)
@All(FpsRenderingComponent.class)
public class FpsUpdatingSystem extends IteratingSystem {
    private static final float FPS_WIDTH = 70;

    private ComponentMapper<AbsoluteCoordinatesComponent> coordinatesComponentMapper;
    private ComponentMapper<TextComponent> textComponentMapper;
    private ComponentMapper<VisibleComponent> visibleComponentMapper;

    @Override
    protected void process(int entityId) {
        TextComponent fpsText = textComponentMapper.has(entityId)
                ? textComponentMapper.get(entityId)
                : initializeFps(entityId);
        fpsText.text("FPS: " + Gdx.graphics.getFramesPerSecond());
    }

    private TextComponent initializeFps(int entityId) {
        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);

        coordinatesComponentMapper.create(entityId).coordinates(
                (float) Gdx.graphics.getWidth() / 2 - FPS_WIDTH,
                (float) Gdx.graphics.getHeight() / 2 - 20,
                0
        );
        visibleComponentMapper.create(entityId);
        return textComponentMapper.create(entityId)
                .font(font)
                .width(FPS_WIDTH);
    }
}
