package ru.jengine.jenginegdx.view.texture;

import com.artemis.PooledComponent;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureComponent extends PooledComponent {
    private TextureRegion region;

    public void texture(Texture texture) {
        this.region = new TextureRegion(texture);
    }

    public void textureRegion(Texture texture, int x, int y, int width, int height) {
        this.region = new TextureRegion(texture, x, y, width, height);
    }

    public TextureRegion getRegion() {
        return region;
    }

    @Override
    protected void reset() {
        this.region = null;
    }
}
