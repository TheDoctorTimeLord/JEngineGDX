package ru.jengine.jenginegdx.utils;

import com.badlogic.gdx.Gdx;

public class DebuggingUtils {
    public static void time(String logTag, Runnable runnable) {
        Gdx.app.log(logTag, "Start loading");
        long startLoading = System.currentTimeMillis();
        runnable.run();
        long endLoading = System.currentTimeMillis();
        Gdx.app.log(logTag, "End loading. Time (s): " + (double)(endLoading - startLoading) / 1000);
    }
}
