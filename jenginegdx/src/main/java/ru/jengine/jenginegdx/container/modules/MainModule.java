package ru.jengine.jenginegdx.container.modules;

import ru.jengine.beancontainer.Constants;
import ru.jengine.beancontainer.annotations.ContainerModule;
import ru.jengine.beancontainer.annotations.PackageScan;
import ru.jengine.beancontainer.annotations.PackagesScan;
import ru.jengine.beancontainer.modules.AnnotationModule;

@ContainerModule(contextName = "jenginegdx", beanSources = Constants.Contexts.DEFAULT_CONTEXT)
@PackagesScan({
        @PackageScan("ru.jengine.jenginegdx.container"),
        @PackageScan("ru.jengine.jenginegdx.view.renderes"),
        @PackageScan("ru.jengine.jenginegdx.viewmodel.ecs")
})
public class MainModule extends AnnotationModule {
}
