package ru.jengine.jenginegdx.view.text;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.viewmodel.ecs.location.components.AbsoluteCoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.rendering.RenderSubsystem;
import ru.jengine.jenginegdx.viewmodel.ecs.text.components.TextComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.text.components.TextPositionComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.text.components.TextStyleComponent;

@Bean
@Order(100)
public class TextRenderSubsystem extends RenderSubsystem {
    private ComponentMapper<AbsoluteCoordinatesComponent> coordinatesComponentMapper;
    private ComponentMapper<TextComponent> textComponentMapper;
    private ComponentMapper<TextPositionComponent> textPositionComponentMapper;
    private ComponentMapper<TextStyleComponent> textStyleComponentMapper;

    @Override
    public void render(int entityId, Batch batch) {
        if (!coordinatesComponentMapper.has(entityId) || !textPositionComponentMapper.has(entityId)
                || !textComponentMapper.has(entityId) || !textStyleComponentMapper.has(entityId))
        {
            return;
        }

        String text = textComponentMapper.get(entityId).getText();
        BitmapFont font = textStyleComponentMapper.get(entityId).getFont();
        TextPositionComponent textPosition = textPositionComponentMapper.get(entityId);

        if (font == null || text == null) {
            return;
        }

        Vector3 coordinates = coordinatesComponentMapper.get(entityId).getCoordinates();
        float x = coordinates.x + textPosition.getXOffsetToText();
        float y = coordinates.y + textPosition.getYOffsetToText();

        font.draw(batch, text, x, y, textPosition.getWidth(), Align.center, true);
    }
}
