package ru.jengine.jenginegdx.utils;

import com.badlogic.gdx.math.Vector2;

public class MathUtils {
    private MathUtils() {}

    public static float getXRotate(Vector2 point, float actualX, float rotation) {
        float sina = com.badlogic.gdx.math.MathUtils.sin(rotation);
        float cosa = com.badlogic.gdx.math.MathUtils.cos(rotation);

        return point.x * cosa - point.y * sina - actualX;
    }

    public static float getYRotate(Vector2 point, float actualY, float rotation) {
        float sina = com.badlogic.gdx.math.MathUtils.sin(rotation);
        float cosa = com.badlogic.gdx.math.MathUtils.cos(rotation);

        return point.x * sina + point.y * cosa - actualY;
    }
}
