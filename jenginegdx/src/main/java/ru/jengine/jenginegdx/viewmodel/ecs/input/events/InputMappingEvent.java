package ru.jengine.jenginegdx.viewmodel.ecs.input.events;

import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.sequence.SequentialEvent;

import java.util.Arrays;

public class InputMappingEvent implements SequentialEvent {
    private final int targetEntityId;
    private final String[] events;

    public InputMappingEvent(int targetEntityId, String... events) {
        this.targetEntityId = targetEntityId;
        this.events = events;
    }

    public int getTargetEntityId() {
        return targetEntityId;
    }

    @Override
    public String[] getSubEventNames() {
        return events;
    }

    @Override
    public String toString() {
        return "UserEvent{" +
                "targetEntityId=" + targetEntityId +
                ", events=" + Arrays.toString(events) +
                '}';
    }
}
