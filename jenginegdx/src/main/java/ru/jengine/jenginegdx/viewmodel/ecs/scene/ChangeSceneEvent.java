package ru.jengine.jenginegdx.viewmodel.ecs.scene;

import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.EventBusEvent;

public record ChangeSceneEvent(int nextScene) implements EventBusEvent {
}
