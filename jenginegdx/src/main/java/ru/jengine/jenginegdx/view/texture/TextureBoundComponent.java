package ru.jengine.jenginegdx.view.texture;

import com.artemis.PooledComponent;

public class TextureBoundComponent extends PooledComponent {
    private float width;
    private float height;

    public void bound(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    @Override
    protected void reset() {
        this.width = 0;
        this.height = 0;
    }
}
