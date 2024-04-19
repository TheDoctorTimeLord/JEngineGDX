package ru.jengine.jenginegdx.container.modules;

import ru.jengine.beancontainer.Constants.Contexts;
import ru.jengine.beancontainer.annotations.ContainerModule;
import ru.jengine.beancontainer.annotations.PackageScan;
import ru.jengine.beancontainer.modules.AnnotationModule;
import ru.jengine.jsonconverter.modules.EnableJsonConverter;

import static ru.jengine.jenginegdx.Constants.Contexts.JENGINE;

@ContainerModule(contextName = JENGINE, beanSources = Contexts.JSON_CONVERTER_CONTEXT)
@PackageScan("ru.jengine.jenginegdx.viewmodel.")
@EnableJsonConverter
public class ViewModelModule extends AnnotationModule {
}
