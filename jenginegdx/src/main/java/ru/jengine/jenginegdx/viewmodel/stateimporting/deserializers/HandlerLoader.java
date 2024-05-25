package ru.jengine.jenginegdx.viewmodel.stateimporting.deserializers;

import com.google.gson.JsonParseException;

import java.lang.reflect.InvocationTargetException;

public class HandlerLoader {
    public static <T> T createHandler(String className, Class<T> availableClass) {
        try {
            Class<?> droppedHandlerClass =
                    DroppedContainerComponentDeserializer.class.getClassLoader().loadClass(className);
            Object droppedHandler = droppedHandlerClass.getConstructor().newInstance();
            if (availableClass.isInstance(droppedHandler)) {
                return (T) droppedHandler;
            }
            throw new JsonParseException("Object of [%s] is not DroppedHandler".formatted(droppedHandlerClass));
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e)
        {
            throw new JsonParseException("Error when creating DroppedHandler [%s]".formatted(className), e);
        }
    }
}
