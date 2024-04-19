package ru.jengine.jenginegdx.viewmodel.ecs;

import com.artemis.Component;

public interface CanBeFilling<T extends CanBeFilling<T>> {
    boolean fill(T other);

    @SuppressWarnings("unchecked")
    default boolean fill(Component component) {
        return fill((T)component);
    }
}
