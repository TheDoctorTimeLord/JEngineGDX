package ru.jengine.jenginegdx.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

public class RenderingBatches implements Disposable {
    private final SpriteBatch spriteBatch;
    private final ShapeRenderer shapeRenderer;

    public RenderingBatches(SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        this.spriteBatch = spriteBatch;
        this.shapeRenderer = shapeRenderer;
    }

    public SpriteBatch forSprite() {
        return spriteBatch;
    }

    public ShapeRenderer forShape() {
        return shapeRenderer;
    }


    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }
}
