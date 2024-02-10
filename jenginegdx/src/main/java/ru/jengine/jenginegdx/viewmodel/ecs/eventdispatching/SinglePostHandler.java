package ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching;

import ru.jengine.eventqueue.event.Event;
import ru.jengine.eventqueue.event.PostHandler;

public abstract class SinglePostHandler<E extends Event> implements PostHandler<E> {
    @Override
    public int getPriority() {
        return 0;
    }
}
