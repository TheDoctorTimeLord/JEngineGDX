package ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.sequence;

import com.badlogic.gdx.Gdx;
import ru.jengine.eventqueue.event.PostHandler;
import ru.jengine.jenginegdx.utils.exceptions.JEngineGdxException;

import java.util.Arrays;
import java.util.List;

public class SequentialEventHandler<E extends SequentialEvent> implements PostHandler<E> {
    private final Class<E> handlingEventType;
    private NamedEventHandlerManager manager;

    public SequentialEventHandler(Class<E> handlingEventType) {
        this.handlingEventType = handlingEventType;
    }

    public void setManager(NamedEventHandlerManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(E event) {
        if (manager == null) {
            return;
        }

        String[] events = event.getSubEventNames();
        List<NamedEventHandler<E>> handlers = manager.getHandlersForEvent(event);

        for (int i = 0; i < events.length; i++) {
            String eventName = events[i];
            NamedEventHandler<E> handler = handlers.get(i);

            try {
                HandlingResult result = handler.handle(eventName, event);
                if (HandlingResult.STOP.equals(result)) {
                    Gdx.app.debug("SEQUENTIAL HANDLER",
                            "Handling events %s by source event [%s] was stopped on [%s] by handler [%s]"
                                    .formatted(Arrays.toString(events), event, eventName, handler));
                    break;
                }
            } catch (Exception e) {
                throw new JEngineGdxException("Error while handling event [%s] by handler [%s]"
                        .formatted(eventName, handlers), e);
            }
        }
    }

    @Override
    public Class<E> getHandlingEventType() {
        return handlingEventType;
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
