package ru.jengine.jenginegdx.modules;

import com.artemis.BaseSystem;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.ContainerModule;
import ru.jengine.beancontainer.annotations.PackageScan;
import ru.jengine.beancontainer.modules.AnnotationModule;
import ru.jengine.jenginegdx.Constants.Contexts;
import ru.jengine.jenginegdx.viewmodel.ecs.worldholder.WorldSystemsHolder;

import java.util.List;

@ContainerModule(contextName = Contexts.JENGINE)
@PackageScan("ru.jengine.jenginegdx.api.")
public class ApiBeansLoader extends AnnotationModule {
    @Bean
    public WorldSystemsHolder systemsHolder(List<BaseSystem> systems) {
        return () -> systems;
    }
}
