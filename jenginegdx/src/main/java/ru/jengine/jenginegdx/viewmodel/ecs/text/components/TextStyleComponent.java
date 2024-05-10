package ru.jengine.jenginegdx.viewmodel.ecs.text.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import ru.jengine.jenginegdx.viewmodel.ecs.ExternalAddable;

@ExternalAddable("textStyle")
public class TextStyleComponent extends PooledComponent {
    private BitmapFont font;

    public TextStyleComponent font(BitmapFont font) {
        this.font = font;
        return this;
    }

    public BitmapFont getFont() {
        return font;
    }

    @Override
    protected void reset() {
        this.font = null;
    }
}
