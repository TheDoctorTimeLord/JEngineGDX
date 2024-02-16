package ru.jengine.jenginegdx.viewmodel.transport.eventbus;

import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.RemoveAfterInitialize;
import ru.jengine.eventqueue.event.PostHandler;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.systems.EventBus;
import ru.jengine.jenginegdx.viewmodel.transport.viewmodel.ViewModelTransportHolder;

@Bean
@RemoveAfterInitialize
public class EventBusAndTransportBinder
{
    private record SendToModelEventHandler(ViewModelTransportHolder transportHolder) implements PostHandler<SendToModelEvent> {
        @Override
        public void handle(SendToModelEvent event) {
            transportHolder.send(event.getEventForSending());
        }

        @Override
        public int getPriority() {
            return 0;
        }
    }

    public EventBusAndTransportBinder(EventBus eventBus, ViewModelTransportHolder transportHolder) {
        transportHolder.setExternalEventListener(eventBus::registerEvent);
        eventBus.registerHandler(new SendToModelEventHandler(transportHolder));
    }
}
