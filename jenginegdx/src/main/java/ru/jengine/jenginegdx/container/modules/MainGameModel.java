package ru.jengine.jenginegdx.container.modules;

import static ru.jengine.beancontainer.Constants.Contexts.DEFAULT_CONTEXT;
import static ru.jengine.jenginegdx.container.modules.Constants.Contexts.JENGINE;

import ru.jengine.beancontainer.annotations.ContainerModule;
import ru.jengine.beancontainer.annotations.PackageScan;
import ru.jengine.beancontainer.modules.AnnotationModule;

@ContainerModule(contextName = JENGINE, beanSources = DEFAULT_CONTEXT)
@PackageScan("ru.jengine.jenginegdx.container")
public class MainGameModel extends AnnotationModule {
}
