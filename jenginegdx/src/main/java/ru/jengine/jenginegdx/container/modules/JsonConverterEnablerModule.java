package ru.jengine.jenginegdx.container.modules;

import ru.jengine.beancontainer.Constants.Contexts;
import ru.jengine.beancontainer.annotations.ContainerModule;
import ru.jengine.beancontainer.annotations.Import;
import ru.jengine.beancontainer.modules.AnnotationModule;
import ru.jengine.jenginegdx.container.jsonreading.JEngineLinkExtractor;
import ru.jengine.jenginegdx.container.jsonreading.JsonResourceLoader;

@ContainerModule(contextName = Contexts.JSON_CONVERTER_CONTEXT)
@Import({JsonResourceLoader.class, JEngineLinkExtractor.class})
public class JsonConverterEnablerModule extends AnnotationModule {
}
