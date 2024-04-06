package ru.jengine.jenginegdx.utils;

import java.security.SecureRandom;

public class RandomUtils {
    private static final SecureRandom random = new SecureRandom();

    public static int nextInt(int from, int to) {
        return random.nextInt(from, to);
    }
}
