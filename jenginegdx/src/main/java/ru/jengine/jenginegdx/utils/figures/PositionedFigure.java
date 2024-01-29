package ru.jengine.jenginegdx.utils.figures;

import com.badlogic.gdx.math.Vector2;

public class PositionedFigure {
    private final FigureBound figureBound;
    private final Vector2 actualPosition;
    private final float rotation;

    public PositionedFigure(FigureBound figureBound, Vector2 actualPosition, float rotation) {
        this.figureBound = figureBound;
        this.actualPosition = actualPosition;
        this.rotation = rotation;
    }

    public boolean inBound(Vector2 checkingPoint) {
        return figureBound.inBound(checkingPoint, actualPosition, rotation);
    }
}
