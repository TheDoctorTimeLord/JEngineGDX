package ru.jengine.jenginegdx.viewmodel.validations.projectvalidators;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.badlogic.gdx.Gdx;
import org.reflections.ReflectionUtils;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.ClassesWith;
import ru.jengine.beancontainer.annotations.RemoveAfterInitialize;
import ru.jengine.jenginegdx.utils.exceptions.JEngineGdxException;
import ru.jengine.jenginegdx.viewmodel.ecs.CanBeFilling;
import ru.jengine.jenginegdx.viewmodel.ecs.ExternalAddable;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Bean
@RemoveAfterInitialize
public class ExternalAddableValidator { //TODO придумать механизм валидаторов
    @SuppressWarnings("unchecked")
    public ExternalAddableValidator(@ClassesWith(ExternalAddable.class) List<Class<?>> classes) {
        for (Class<?> componentClass : classes) {
            if (!Component.class.isAssignableFrom(componentClass)) {
                throw new JEngineGdxException("Class [%s] annotated %s must extend [%s]"
                        .formatted(componentClass, ExternalAddable.class.getSimpleName(), Component.class));
            }
            if (ReflectionUtils.getAllFields(componentClass).isEmpty() && !CanBeFilling.class.isAssignableFrom(componentClass)) {
                Gdx.app.debug("WARN", "Component [%s] can be added external but can not be filled".formatted(componentClass));
            }
            Set<Field> badLinkFields = ReflectionUtils.getAllFields(componentClass, ExternalAddableValidator::hasBadLinks);
            if (!badLinkFields.isEmpty()) {
                throw new JEngineGdxException(("Engine is not supported link to %s on components annotated %s. Found: %s")
                        .formatted(Entity.class.getSimpleName(), ExternalAddable.class.getSimpleName(),
                                badLinkFields.stream().map(Field::getName).collect(Collectors.joining(", "))));
            }
        }
    }

    private static boolean hasBadLinks(Field field) {
        if (field.getDeclaringClass().equals(Entity.class)) {
            return true;
        }
        return field.getGenericType() instanceof ParameterizedType type
                && type.getRawType() == Bag.class && type.getActualTypeArguments()[0] == Entity.class;
    }
}
