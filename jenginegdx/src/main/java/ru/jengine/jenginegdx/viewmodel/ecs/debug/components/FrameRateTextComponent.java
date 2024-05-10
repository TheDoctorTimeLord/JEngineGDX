package ru.jengine.jenginegdx.viewmodel.ecs.debug.components;

import com.artemis.PooledComponent;

public class FrameRateTextComponent extends PooledComponent {
    private String frameRateText;

    public void frameRateText(int frameRate) {
        this.frameRateText = "FPS: " + frameRate;
    }

    public String getFrameRateText() {
        return frameRateText;
    }

    @Override
    protected void reset() {
        this.frameRateText = null;
    }
}
