package ru.jengine.jenginegdx.utils.figures;

import com.badlogic.gdx.math.Vector2;
import com.google.common.base.Preconditions;
import ru.jengine.jenginegdx.utils.MathUtils;

public class RectangleBound implements FigureBound {
    private final float width;
    private final float height;

    public RectangleBound(float width, float height) {
        Preconditions.checkArgument(width > 0, "Width can not be bellow zero");
        Preconditions.checkArgument(height > 0, "Height can not be bellow zero");

        this.width = width;
        this.height = height;
    }

    @Override
    public boolean inBound(Vector2 point, Vector2 actualPosition, float rotation) {
        float newPointX = MathUtils.getXRotate(point, actualPosition.x, rotation);
        float newPointY = MathUtils.getYRotate(point, actualPosition.y, rotation);

        return 0 <= newPointX && newPointX <= width && 0 <= newPointY && newPointY <= height;
    }
}
