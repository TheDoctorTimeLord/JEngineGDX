package ru.jengine.jenginegdx.viewmodel.transport;

import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.viewmodel.transport.model.ModelTransportHolder;
import ru.jengine.jenginegdx.viewmodel.transport.variants.RAMTransport;
import ru.jengine.jenginegdx.viewmodel.transport.viewmodel.ViewModelTransportHolder;

@Bean
public class TransportSettings //TODO переделать на разные способы запуска игры (offline, online)
{
    public TransportSettings(ViewModelTransportHolder viewModelTransportHolder, ModelTransportHolder modelTransportHolder) {
        RAMTransport ramTransport = new RAMTransport();

        viewModelTransportHolder.setViewModelTransport(ramTransport.asViewModelTransport());
        modelTransportHolder.setModelTransport(ramTransport.asModelTransport());
    }
}
