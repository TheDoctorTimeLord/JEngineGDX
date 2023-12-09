package ru.jengine.jenginegdx.container.modules;

import static ru.jengine.jenginegdx.container.modules.Constants.Contexts.JENGINE;

import ru.jengine.beancontainer.annotations.ContainerModule;
import ru.jengine.beancontainer.annotations.PackageScan;
import ru.jengine.beancontainer.modules.AnnotationModule;

@ContainerModule(contextName = JENGINE)
@PackageScan("ru.jengine.jenginegdx.view.renderes")
public class ViewModule extends AnnotationModule {
}
