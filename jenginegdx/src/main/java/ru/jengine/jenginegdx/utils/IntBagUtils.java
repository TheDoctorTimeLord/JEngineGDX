package ru.jengine.jenginegdx.utils;

import com.artemis.utils.IntBag;

import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IntBagUtils {
    private IntBagUtils() {}

    public static void forEach(IntBag intBag, IntConsumer callback) {
        int[] ints = intBag.getData();
        for (int i = 0, size = intBag.size(); i < size; i++) {
            callback.accept(ints[i]);
        }
    }

    public static <T> Stream<T> map(IntBag intBag, IntFunction<T> mapper) {
        int[] ints = intBag.getData();
        return IntStream.range(0, intBag.size())
                .mapToObj(ind -> mapper.apply(ints[ind]));
    }
}
