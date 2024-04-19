package ru.jengine.jenginegdx.viewmodel.ecs.cleaning;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CanBeDirty {
}
