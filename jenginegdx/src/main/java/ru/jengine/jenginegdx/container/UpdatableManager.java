package ru.jengine.jenginegdx.container;

import ru.jengine.beancontainer.annotations.Bean;

import java.util.List;

@Bean
public class UpdatableManager {
    private final List<Updatable> updatableCollections;

    public UpdatableManager(List<Updatable> updatableCollections) {
        this.updatableCollections = updatableCollections;
    }

    public void updateSystems() {
        for (Updatable updatable : updatableCollections) {
            updatable.update();
        }
    }
}
