package ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.sequence;

import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.utils.exceptions.JEngineGdxException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Bean
public class NamedEventHandlerManager {
    private final Map<Class<?>, Map<String, NamedEventHandler<?>>> namedEventHandlers = new HashMap<>();

    public void registerHandler(NamedEventHandler<?> namedEventHandler) {
        String[] eventNames = namedEventHandler.getHandlingEventNames();
        Map<String, NamedEventHandler<?>> handlersByEvents = getHandlingMapprings(namedEventHandler);

        //Проверим наличие обработчика для событий, чтобы недопустить частичной регистрации обработчика
        for (String eventName : eventNames) {
            if (handlersByEvents.containsKey(eventName)) {
                throw new JEngineGdxException("Handler of subevent [%s] already registered".formatted(eventName));
            }
        }

        for (String eventName : eventNames) {
            handlersByEvents.put(eventName, namedEventHandler);
        }
    }

    public void removeHandler(NamedEventHandler<?> namedEventHandler) {
        Map<String, NamedEventHandler<?>> handlersByEvents = getHandlingMapprings(namedEventHandler);
        for (String eventName : namedEventHandler.getHandlingEventNames()) {
            handlersByEvents.remove(eventName);
        }
    }

    public void clearHandlers() {
        namedEventHandlers.clear();
    }

    @SuppressWarnings("unchecked")
    public <E extends SequentialEvent> List<NamedEventHandler<E>> getHandlersForEvent(E sourceEvent) {
        Map<String, NamedEventHandler<?>> handlerMappings = namedEventHandlers.getOrDefault(sourceEvent.getClass(), Map.of());
        return Arrays.stream(sourceEvent.getSubEventNames())
                .map(event -> {
                    NamedEventHandler<E> namedEventHandler = (NamedEventHandler<E>) handlerMappings.get(event);
                    if (namedEventHandler == null) {
                        throw new JEngineGdxException("Handler of event [%s] with source [%s] not found"
                                .formatted(event, sourceEvent));
                    }
                    return namedEventHandler;
                })
                .toList();
    }

    private Map<String, NamedEventHandler<?>> getHandlingMapprings(NamedEventHandler<?> handler) {
        return namedEventHandlers.computeIfAbsent(handler.getSourceEventType(), cls -> new HashMap<>());
    }
}
