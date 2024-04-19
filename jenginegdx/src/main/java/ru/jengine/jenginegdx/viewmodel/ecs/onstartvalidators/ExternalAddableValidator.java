package ru.jengine.jenginegdx.viewmodel.ecs.onstartvalidators;

import com.artemis.Component;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.ClassesWith;
import ru.jengine.beancontainer.annotations.RemoveAfterInitialize;
import ru.jengine.beancontainer.exceptions.ContainerException;
import ru.jengine.jenginegdx.viewmodel.ecs.ExternalAddable;

import java.util.List;

@Bean
@RemoveAfterInitialize
public class ExternalAddableValidator {
    public ExternalAddableValidator(@ClassesWith(ExternalAddable.class) List<Class<?>> classes) {
        for (Class<?> componentClass : classes) {
            if (!Component.class.isAssignableFrom(componentClass)) {
                throw new ContainerException("Class [%s] annotated ExternalAddable must extend [%s]"
                        .formatted(componentClass, Component.class));
            }
        }
    }
}
