package ru.jengine.jenginegdx.viewmodel.ecs.text.components;

import com.artemis.PooledComponent;
import ru.jengine.jenginegdx.utils.exceptions.JEngineGdxException;
import ru.jengine.jenginegdx.viewmodel.ecs.CanBeFilling;
import ru.jengine.jenginegdx.viewmodel.ecs.ExternalAddable;

@ExternalAddable("textPosition")
public class TextPositionComponent extends PooledComponent implements CanBeFilling<TextPositionComponent> {
    private float width;
    private float xOffsetToText;
    private float yOffsetToText;

    public TextPositionComponent width(float width) {
        if (width < 0) throw new JEngineGdxException("Text width can not be less then 0. Was set: " + width);
        this.width = width;
        return this;
    }

    public TextPositionComponent textPosition(float xOffset, float yOffset) {
        this.xOffsetToText = xOffset;
        this.yOffsetToText = yOffset;
        return this;
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
        this.width = 0;
        textPosition(0, 0);
    }

    @Override
    public boolean fill(TextPositionComponent other) {
        this.width = other.width;
        this.xOffsetToText = other.xOffsetToText;
        this.yOffsetToText = other.yOffsetToText;
        return width > 0;
    }
}
