package ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.sequence;

public class EmptyNamedEventHandler<E extends SequentialEvent> implements NamedEventHandler<E> {
    public static final EmptyNamedEventHandler<?> INSTANCE = new EmptyNamedEventHandler<>();

    @SuppressWarnings("unchecked")
    public static <E extends SequentialEvent> NamedEventHandler<E> getInstance() {
        return (NamedEventHandler<E>) INSTANCE;
    }

    @Override
    public String[] getHandlingEventNames() {
        return new String[0];
    }

    @Override
    public HandlingResult handle(String eventName, SequentialEvent sourceEvent) {
        return HandlingResult.PROCESS;
    }
}
