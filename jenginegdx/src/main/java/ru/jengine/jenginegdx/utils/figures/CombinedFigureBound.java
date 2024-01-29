package ru.jengine.jenginegdx.utils.figures;

import com.badlogic.gdx.math.Vector2;
import ru.jengine.jenginegdx.utils.MathUtils;

public class CombinedFigureBound implements FigureBound {
    private final PositionedFigure[] figures;

    public CombinedFigureBound(PositionedFigure... figures) {
        this.figures = figures;
    }

    @Override
    public boolean inBound(Vector2 point, Vector2 actualPosition, float rotation) {
        Vector2 transformedPoint = new Vector2(
                MathUtils.getXRotate(point, actualPosition.x, rotation),
                MathUtils.getYRotate(point, actualPosition.y, rotation)
        );

        for (PositionedFigure figure : figures) {
            if (figure.inBound(transformedPoint)) {
                return true;
            }
        }
        return false;
    }
}
