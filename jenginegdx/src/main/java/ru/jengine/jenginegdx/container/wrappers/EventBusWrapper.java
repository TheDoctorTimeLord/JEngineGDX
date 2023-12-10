package ru.jengine.jenginegdx.container.wrappers;

import java.util.List;

import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.PreDestroy;
import ru.jengine.eventqueue.Dispatcher;
import ru.jengine.eventqueue.event.PostHandler;
import ru.jengine.jenginegdx.container.Updatable;
import ru.jengine.jenginegdx.viewmodel.eventbus.ClientEventPoolHandler;
import ru.jengine.jenginegdx.viewmodel.eventbus.EventBusEvent;

@Bean
public class EventBusWrapper implements Updatable
{
    private final Dispatcher dispatcher;
    private final ClientEventPoolHandler clientEventPoolHandler;

    public EventBusWrapper(Dispatcher dispatcher, ClientEventPoolHandler clientEventPoolHandler)
    {
        this.dispatcher = dispatcher;
        this.clientEventPoolHandler = clientEventPoolHandler;

        dispatcher.registerEventPoolHandler(List.of(clientEventPoolHandler), clientEventPoolHandler, List.of());
    }

    public void registerHandler(PostHandler<?> handler) {
        dispatcher.registerPostHandlerToPool(clientEventPoolHandler.getEventPoolCode(), handler);
    }

    public void registerEvent(EventBusEvent event) {
        dispatcher.registerEvent(event);
    }

    @Override
    public void update() {
        dispatcher.handleEvents(clientEventPoolHandler.getEventPoolCode());
    }

    @PreDestroy
    private void stop() {
        dispatcher.removeEventPoolHandler(List.of(clientEventPoolHandler), clientEventPoolHandler.getEventPoolCode());
    }
}
