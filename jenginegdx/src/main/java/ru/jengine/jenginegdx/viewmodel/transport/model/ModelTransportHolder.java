package ru.jengine.jenginegdx.viewmodel.transport.model;

import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.viewmodel.transport.Transport;

@Bean
public class ModelTransportHolder implements Transport<ModelTransportEvent, FromViewModelEventListener> {
    private ModelTransport modelTransport;
    private FromViewModelEventListener listener;

    public void setModelTransport(ModelTransport modelTransport) {
        this.modelTransport = modelTransport;

        if (listener != null) {
            modelTransport.setExternalEventListener(listener);
        }
    }

    @Override
    public void send(ModelTransportEvent event) {
        modelTransport.send(event);
    }

    @Override
    public void setExternalEventListener(FromViewModelEventListener listener) {
        this.listener = listener;

        if (modelTransport != null) {
            modelTransport.setExternalEventListener(listener);
        }
    }
}
