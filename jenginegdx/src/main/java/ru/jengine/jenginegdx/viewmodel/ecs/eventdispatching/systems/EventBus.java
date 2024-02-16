package ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.systems;

import com.artemis.BaseSystem;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.beancontainer.annotations.PreDestroy;
import ru.jengine.eventqueue.Dispatcher;
import ru.jengine.eventqueue.event.PostHandler;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.ClientEventPoolHandler;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.EventBusEvent;

import java.util.List;

@Bean
@Order(100)
public class EventBus extends BaseSystem
{
    private final Dispatcher dispatcher;
    private final ClientEventPoolHandler clientEventPoolHandler;

    public EventBus(Dispatcher dispatcher, ClientEventPoolHandler clientEventPoolHandler)
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
    protected void processSystem() {
        dispatcher.handleEvents(clientEventPoolHandler.getEventPoolCode());
    }

    @PreDestroy
    private void stop() {
        dispatcher.removeEventPoolHandler(List.of(clientEventPoolHandler), clientEventPoolHandler.getEventPoolCode());
    }
}
