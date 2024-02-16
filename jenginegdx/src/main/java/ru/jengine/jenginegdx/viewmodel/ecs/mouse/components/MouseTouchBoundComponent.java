package ru.jengine.jenginegdx.viewmodel.ecs.mouse.components;

import com.artemis.PooledComponent;
import ru.jengine.jenginegdx.utils.MathUtils;

public class MouseTouchBoundComponent extends PooledComponent {
    private float width;
    private float height;
    private float boundOffsetX;
    private float boundOffsetY;

    public void bounds(float width, float height) {
        bounds(width, height, 0, 0);
    }

    public void bounds(float width, float height, float offsetX, float offsetY) {
        this.width = width;
        this.height = height;
        this.boundOffsetX = offsetX;
        this.boundOffsetY = offsetY;
    }

    public boolean inBound(float x, float y, float boundStartX, float boundStartY, float rotation) {
        float actualX = MathUtils.getXRotate(x, y, boundStartX + boundOffsetX, rotation);
        float actualY = MathUtils.getYRotate(x, y, boundStartY + boundOffsetY, rotation);

        return 0 <= actualX && actualX <= width && 0 <= actualY && actualY <= height;
    }

    @Override
    protected void reset() {
        bounds(0, 0, 0, 0);
    }
}