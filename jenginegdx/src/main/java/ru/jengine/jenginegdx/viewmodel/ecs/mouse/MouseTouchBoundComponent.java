package ru.jengine.jenginegdx.viewmodel.ecs.mouse;

import com.artemis.PooledComponent;
import ru.jengine.jenginegdx.utils.MathUtils;

public class MouseTouchBoundComponent extends PooledComponent {
    private float width;
    private float height;

    public void setBounds(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public boolean inBound(float x, float y, float boundStartX, float boundStartY, float rotation) {
        float actualX = MathUtils.getXRotate(x, y, boundStartX, rotation);
        float actualY = MathUtils.getYRotate(x, y, boundStartY, rotation);

        return 0 <= actualX && actualX <= width && 0 <= actualY && actualY <= height;
    }

    @Override
    protected void reset() {

    }
}