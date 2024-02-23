package ru.jengine.jenginegdx.viewmodel.ecs.mouse.components;

import com.artemis.PooledComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.cleaning.CleanableComponentMarker;

@CleanableComponentMarker
public class MouseTouchedComponent extends PooledComponent {
    private float x;
    private float y;

    public void setTouched(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    protected void reset() {
        this.x = 0;
        this.y = 0;
    }
}
