package ru.jengine.jenginegdx.container.modules;

import static ru.jengine.jenginegdx.container.modules.Constants.Contexts.JENGINE;

import ru.jengine.beancontainer.annotations.ContainerModule;
import ru.jengine.beancontainer.annotations.Import;
import ru.jengine.beancontainer.annotations.PackageScan;
import ru.jengine.beancontainer.annotations.PackagesScan;
import ru.jengine.beancontainer.modules.AnnotationModule;
import ru.jengine.jenginegdx.viewmodel.transport.TransportSettings;
import ru.jengine.jenginegdx.viewmodel.transport.eventbus.EventBusAndTransportBinder;
import ru.jengine.jenginegdx.viewmodel.transport.model.ModelTransportHolder;
import ru.jengine.jenginegdx.viewmodel.transport.viewmodel.ViewModelTransportHolder;

@ContainerModule(contextName = JENGINE)
@PackagesScan({
        @PackageScan("ru.jengine.jenginegdx.viewmodel.ecs"),
        @PackageScan("ru.jengine.jenginegdx.viewmodel.eventbus")
})
@Import({ViewModelTransportHolder.class, ModelTransportHolder.class, TransportSettings.class, EventBusAndTransportBinder.class})
public class ViewModelModule extends AnnotationModule {
}
