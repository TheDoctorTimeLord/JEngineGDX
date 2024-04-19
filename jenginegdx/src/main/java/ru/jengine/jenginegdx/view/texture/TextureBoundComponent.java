package ru.jengine.jenginegdx.view.texture;

import com.artemis.PooledComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.CanBeFilling;
import ru.jengine.jenginegdx.viewmodel.ecs.ExternalAddable;

@ExternalAddable("textureBound")
public class TextureBoundComponent extends PooledComponent implements CanBeFilling<TextureBoundComponent> {
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

    @Override
    public boolean fill(TextureBoundComponent other) {
        this.width = other.width;
        this.height = other.height;
        return width > 0 && height > 0;
    }
}
