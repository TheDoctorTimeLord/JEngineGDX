package ru.jengine.jenginegdx.container.modules;

import static ru.jengine.jenginegdx.container.modules.Constants.Contexts.JENGINE;

import ru.jengine.beancontainer.annotations.ContainerModule;
import ru.jengine.beancontainer.annotations.Import;
import ru.jengine.beancontainer.annotations.PackageScan;
import ru.jengine.beancontainer.modules.AnnotationModule;
import ru.jengine.jenginegdx.viewmodel.transport.viewmodel.ViewModelTransportAdapter;

@ContainerModule(contextName = JENGINE)
@PackageScan("ru.jengine.jenginegdx.viewmodel.ecs")
@Import(ViewModelTransportAdapter.class)
public class ViewModelModule extends AnnotationModule {
}
