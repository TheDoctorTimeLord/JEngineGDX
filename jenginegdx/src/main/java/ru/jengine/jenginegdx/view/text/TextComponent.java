package ru.jengine.jenginegdx.view.text;

import com.artemis.PooledComponent;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import ru.jengine.jenginegdx.viewmodel.ecs.CanBeFilling;
import ru.jengine.jenginegdx.utils.exceptions.JEngineGdxException;
import ru.jengine.jenginegdx.viewmodel.ecs.ExternalAddable;

@ExternalAddable("text")
public class TextComponent extends PooledComponent implements CanBeFilling<TextComponent> {
    private BitmapFont font;
    private String text;
    private float width;
    private float xOffsetToText;
    private float yOffsetToText;

    public TextComponent font(BitmapFont font) {
        this.font = font;
        return this;
    }

    public TextComponent text(String text) {
        this.text = text;
        return this;
    }

    public TextComponent width(float width) {
        if (width < 0) throw new JEngineGdxException("Text width can not be less then 0. Was set: " + width);
        this.width = width;
        return this;
    }

    public TextComponent textPosition(float xOffset, float yOffset) {
        this.xOffsetToText = xOffset;
        this.yOffsetToText = yOffset;
        return this;
    }

    public BitmapFont getFont() {
        return font;
    }

    public String getText() {
        return text;
    }

    public float getWidth() {
        return width;
    }

    public float getXOffsetToText() {
        return xOffsetToText;
    }

    public float getYOffsetToText() {
        return yOffsetToText;
    }

    @Override
    protected void reset() {
        this.font = null;
        this.text = null;
        this.width = 0;
        textPosition(0, 0);
    }

    @Override
    public boolean fill(TextComponent other) {
        this.font = other.font;
        this.text = other.text;
        this.width = other.width;
        this.xOffsetToText = other.xOffsetToText;
        this.yOffsetToText = other.yOffsetToText;
        return font != null && text != null && width > 0;
    }
}
