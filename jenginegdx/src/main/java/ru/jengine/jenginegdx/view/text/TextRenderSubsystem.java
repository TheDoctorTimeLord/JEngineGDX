package ru.jengine.jenginegdx.view.text;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.viewmodel.ecs.rendering.RenderSubsystem;

@Bean
public class TextRenderSubsystem implements RenderSubsystem {
    private ComponentMapper<TextComponent> textComponentMapper;

    @Override
    public void render(int entityId, Batch batch) {
        if (!textComponentMapper.has(entityId)) {
            return;
        }

        TextComponent textComponent = textComponentMapper.get(entityId);
        BitmapFont font = textComponent.getFont();
        String text = textComponent.getText();
        if (font == null || text == null) {
            return;
        }

        font.draw(batch, text, textComponent.getTextX(), textComponent.getTextY(), textComponent.getWidth(), Align.center, true);
    }
}
