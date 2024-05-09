package ru.jengine.jenginegdx.view.text;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.viewmodel.ecs.location.components.AbsoluteCoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.rendering.RenderSubsystem;

@Bean
public class TextRenderSubsystem extends RenderSubsystem {
    private ComponentMapper<AbsoluteCoordinatesComponent> coordinatesComponentMapper;
    private ComponentMapper<TextComponent> textComponentMapper;

    @Override
    public void render(int entityId, Batch batch) {
        if (!coordinatesComponentMapper.has(entityId) || !textComponentMapper.has(entityId)) {
            return;
        }

        TextComponent textComponent = textComponentMapper.get(entityId);

        BitmapFont font = textComponent.getFont();
        String text = textComponent.getText();
        if (font == null || text == null) {
            return;
        }

        Vector3 coordinates = coordinatesComponentMapper.get(entityId).getCoordinates();
        float x = coordinates.x + textComponent.getXOffsetToText();
        float y = coordinates.y + textComponent.getYOffsetToText();

        font.draw(batch, text, x, y, textComponent.getWidth(), Align.center, true);
    }
}
