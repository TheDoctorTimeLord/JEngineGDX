package ru.jengine.jenginegdx.viewmodel.ecs.label;

import com.artemis.PooledComponent;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;

public class LabelComponent extends PooledComponent { //TODO пересмотреть компонент
    public BitmapFontCache fontCache;
    public String text;
    public float x;
    public float y;

    public LabelComponent font(BitmapFont font) {
        this.fontCache = font.newFontCache();
        return this;
    }

    public LabelComponent text(String text) {
        this.text = text;
        setTextOnPosition(text, x, y);
        return this;
    }

    public LabelComponent position(float x, float y) {
        this.x = x;
        this.y = y;
        if (text != null) {
            setTextOnPosition(text, x, y);
        }
        return this;
    }

    private void setTextOnPosition(String text, float x, float y) {
        this.fontCache.setText(text, x, y);
    }

    @Override
    protected void reset() {
        fontCache.clear();
        x = 0;
        y = 0;
        text = null;
    }
}
