package ru.jengine.jenginegdx.viewmodel.ecs;

import ru.jengine.beancontainer.annotations.Bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Bean //TODO удалить @Bean, когда выполнено нижнее
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface InputComponentMarker { //TODO сделать возможность получения всех маркерованных объектов контейнера
}
