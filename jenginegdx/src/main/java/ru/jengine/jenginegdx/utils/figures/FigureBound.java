package ru.jengine.jenginegdx.utils.figures;

import com.badlogic.gdx.math.Vector2;

public interface FigureBound {
    boolean inBound(Vector2 point, Vector2 actualPosition, float rotation);
}
