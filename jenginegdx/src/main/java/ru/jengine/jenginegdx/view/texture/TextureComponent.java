package ru.jengine.jenginegdx.view.texture;

import com.artemis.PooledComponent;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ru.jengine.jenginegdx.viewmodel.ecs.CanBeFilling;
import ru.jengine.jenginegdx.viewmodel.ecs.ExternalAddable;

@ExternalAddable("texture")
public class TextureComponent extends PooledComponent implements CanBeFilling<TextureComponent> {
    private TextureRegion region;

    public TextureComponent texture(Texture texture) {
        this.region = new TextureRegion(texture);
        return this;
    }

    public TextureComponent textureRegion(Texture texture, int x, int y, int width, int height) {
        this.region = new TextureRegion(texture, x, y, width, height);
        return this;
    }

    public TextureRegion getRegion() {
        return region;
    }

    @Override
    protected void reset() {
        this.region = null;
    }

    @Override
    public boolean fill(TextureComponent other) {
        this.region = other.region;
        return region != null;
    }
}
