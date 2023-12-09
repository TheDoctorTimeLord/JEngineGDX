package ru.jengine.jenginegdx.viewmodel.transport.model;

import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.viewmodel.transport.Transport;

@Bean
public class ModelTransportAdapter implements Transport<ModelTransportEvent, FromViewModelEventListener> {
    private ModelTransport modelTransport;

    public void setModelTransport(ModelTransport modelTransport) {
        this.modelTransport = modelTransport;
    }

    @Override
    public void send(ModelTransportEvent event) {
        modelTransport.send(event);
    }

    @Override
    public void setExternalEventListener(FromViewModelEventListener listener) {
        modelTransport.setExternalEventListener(listener);
    }
}
