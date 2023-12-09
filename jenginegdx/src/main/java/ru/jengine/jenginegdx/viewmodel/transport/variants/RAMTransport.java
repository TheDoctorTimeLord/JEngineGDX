package ru.jengine.jenginegdx.viewmodel.transport.variants;

import ru.jengine.jenginegdx.viewmodel.transport.model.FromViewModelEventListener;
import ru.jengine.jenginegdx.viewmodel.transport.model.ModelTransport;
import ru.jengine.jenginegdx.viewmodel.transport.model.ModelTransportEvent;
import ru.jengine.jenginegdx.viewmodel.transport.viewmodel.FromModelEventListener;
import ru.jengine.jenginegdx.viewmodel.transport.viewmodel.ViewModelTransport;
import ru.jengine.jenginegdx.viewmodel.transport.viewmodel.ViewModelTransportEvent;

public class RAMTransport {
    private final RAMModelTransport modelTransport;
    private final RAMViewModelTransport viewModelTransport;

    public RAMTransport()
    {
        this.modelTransport = new RAMModelTransport();
        this.viewModelTransport = new RAMViewModelTransport();

        this.modelTransport.viewModelTransport = viewModelTransport;
        this.viewModelTransport.modelTransport = modelTransport;
    }

    public ModelTransport asModelTransport()
    {
        return modelTransport;
    }

    public ViewModelTransport asViewModelTransport()
    {
        return viewModelTransport;
    }

    private static class RAMModelTransport implements ModelTransport {
        private FromViewModelEventListener fromExternalListener;
        private RAMViewModelTransport viewModelTransport;

        @Override
        public void send(ModelTransportEvent event)
        {
            viewModelTransport.fromModelEventListener.handleListenedEvent(event);
        }

        @Override
        public void setExternalEventListener(FromViewModelEventListener listener)
        {
            this.fromExternalListener = listener;
        }
    }

    private static class RAMViewModelTransport implements ViewModelTransport {
        private FromModelEventListener fromModelEventListener;
        private RAMModelTransport modelTransport;

        @Override
        public void send(ViewModelTransportEvent event)
        {
            modelTransport.fromExternalListener.handleListenedEvent(event);
        }

        @Override
        public void setExternalEventListener(FromModelEventListener listener)
        {
            this.fromModelEventListener = listener;
        }
    }
}
