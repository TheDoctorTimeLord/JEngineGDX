package ru.jengine.jenginegdx.viewmodel.transport.viewmodel;

import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.viewmodel.transport.Transport;

@Bean
public class ViewModelTransportHolder implements Transport<ViewModelTransportEvent, FromModelEventListener> {
    private ViewModelTransport viewModelTransport;
    private FromModelEventListener listener;

    public void setViewModelTransport(ViewModelTransport viewModelTransport) {
        this.viewModelTransport = viewModelTransport;

        if (listener != null) {
            viewModelTransport.setExternalEventListener(listener);
        }
    }

    @Override
    public void send(ViewModelTransportEvent event) {
        viewModelTransport.send(event);
    }

    @Override
    public void setExternalEventListener(FromModelEventListener listener) {
        this.listener = listener;

        if (viewModelTransport != null) {
            viewModelTransport.setExternalEventListener(listener);
        }
    }
}
