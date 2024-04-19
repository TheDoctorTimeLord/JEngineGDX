package ru.jengine.jenginegdx.container.modules;

import ru.jengine.beancontainer.annotations.ContainerModule;
import ru.jengine.beancontainer.annotations.PackageScan;
import ru.jengine.beancontainer.modules.AnnotationModule;

import static ru.jengine.beancontainer.Constants.Contexts.DEFAULT_CONTEXT;
import static ru.jengine.jenginegdx.Constants.Contexts.JENGINE;

@ContainerModule(contextName = JENGINE, beanSources = DEFAULT_CONTEXT)
@PackageScan("ru.jengine.jenginegdx.container.")
public class MainGameModule extends AnnotationModule {
}
