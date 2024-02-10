package ru.jengine.jenginegdx.utils;

public class MathUtils {
    private MathUtils() {}

    public static float getXRotate(float x, float y, float actualX, float rotation) {
        float sina = com.badlogic.gdx.math.MathUtils.sin(rotation);
        float cosa = com.badlogic.gdx.math.MathUtils.cos(rotation);

        return x * cosa - y * sina - actualX;
    }

    public static float getYRotate(float x, float y, float actualY, float rotation) {
        float sina = com.badlogic.gdx.math.MathUtils.sin(rotation);
        float cosa = com.badlogic.gdx.math.MathUtils.cos(rotation);

        return x * sina + y * cosa - actualY;
    }
}
