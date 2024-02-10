package ru.jengine.jenginegdx.container;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class JEngineGdxConfiguration {
    private Camera camera = new OrthographicCamera();
    private Batch batch = new SpriteBatch();

    public static JEngineGdxConfiguration build() {
        return new JEngineGdxConfiguration();
    }

    private JEngineGdxConfiguration() {}

    public JEngineGdxConfiguration camera(Camera camera) {
        this.camera = camera;
        return this;
    }

    public JEngineGdxConfiguration batch(SpriteBatch batch) {
        this.batch = batch;
        return this;
    }

    public Camera getCamera() {
        return camera;
    }

    public Batch getBatch() {
        return batch;
    }
}
