package ru.jengine.jenginegdx.viewmodel.stateimporting.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Shared;
import ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.DroppedHandler;
import ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components.DroppedContainerComponent;
import ru.jengine.jsonconverter.formatting.FormatterContext;
import ru.jengine.jsonconverter.serializeprocess.JsonConverterDeserializer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

@Bean
@Shared
public class DroppedContainerComponentDeserializer implements JsonConverterDeserializer<DroppedContainerComponent> {
    @Override
    public DroppedContainerComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonObject()) {
            return null;
        }

        JsonObject jsonObject = json.getAsJsonObject();
        String targetType = FormatterContext.asString(jsonObject, "targetDraggingType");
        String droppedHandlerClassName = FormatterContext.asString(jsonObject, "droppedHandler");
        DroppedHandler droppedHandler = createByClassName(droppedHandlerClassName);

        return new DroppedContainerComponent().droppedHandler(targetType, droppedHandler);
    }

    private static DroppedHandler createByClassName(String droppedHandlerClassName) {
        try {
            Class<?> droppedHandlerClass =
                    DroppedContainerComponentDeserializer.class.getClassLoader().loadClass(droppedHandlerClassName);
            Object droppedHandler = droppedHandlerClass.getConstructor().newInstance();
            if (!(droppedHandler instanceof DroppedHandler handler)) {
                throw new JsonParseException("Object of [%s] is not DroppedHandler".formatted(droppedHandlerClass));
            }
            return handler;
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e)
        {
            throw new JsonParseException("Error when creating DroppedHandler [%s]".formatted(droppedHandlerClassName), e);
        }
    }
}
