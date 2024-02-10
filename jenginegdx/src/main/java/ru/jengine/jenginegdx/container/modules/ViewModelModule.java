package ru.jengine.jenginegdx.container.modules;

import ru.jengine.beancontainer.annotations.ContainerModule;
import ru.jengine.beancontainer.annotations.PackageScan;
import ru.jengine.beancontainer.modules.AnnotationModule;

import static ru.jengine.jenginegdx.Constants.Contexts.JENGINE;

@ContainerModule(contextName = JENGINE)
@PackageScan("ru.jengine.jenginegdx.viewmodel")
public class ViewModelModule extends AnnotationModule {
}
