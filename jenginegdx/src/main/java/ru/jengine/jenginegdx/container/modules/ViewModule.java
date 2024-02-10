package ru.jengine.jenginegdx.container.modules;

import ru.jengine.beancontainer.annotations.ContainerModule;
import ru.jengine.beancontainer.annotations.PackageScan;
import ru.jengine.beancontainer.annotations.PackagesScan;
import ru.jengine.beancontainer.modules.AnnotationModule;

import static ru.jengine.jenginegdx.Constants.Contexts.JENGINE;

@ContainerModule(contextName = JENGINE)
@PackagesScan({
        @PackageScan("ru.jengine.jenginegdx.view.texture") //TODO исправить баг с чтением путей (читает как view, так и viewmodel)
})
public class ViewModule extends AnnotationModule {
}
