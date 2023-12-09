package ru.jengine.jenginegdx.viewmodel.transport.viewmodel;

import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.viewmodel.transport.Transport;

@Bean
public class ViewModelTransportAdapter implements Transport<ViewModelTransportEvent, FromModelEventListener> {
    private ViewModelTransport viewModelTransport;

    public void setViewModelTransport(ViewModelTransport viewModelTransport) {
        this.viewModelTransport = viewModelTransport;
    }

    @Override
    public void send(ViewModelTransportEvent event) {
        viewModelTransport.send(event);
    }

    @Override
    public void setExternalEventListener(FromModelEventListener listener) {
        viewModelTransport.setExternalEventListener(listener);
    }
}
