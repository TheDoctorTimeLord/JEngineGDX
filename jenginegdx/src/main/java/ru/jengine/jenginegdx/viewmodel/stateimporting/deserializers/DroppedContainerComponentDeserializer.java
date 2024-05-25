package ru.jengine.jenginegdx.viewmodel.stateimporting.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Shared;
import ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.DroppedHandler;
import ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components.DroppedContainerComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.worldholder.WorldHolder;
import ru.jengine.jsonconverter.formatting.FormatterContext;
import ru.jengine.jsonconverter.serializeprocess.JsonConverterDeserializer;

import java.lang.reflect.Type;

@Bean
@Shared
public class DroppedContainerComponentDeserializer implements JsonConverterDeserializer<DroppedContainerComponent> {
    private final WorldHolder worldHolder;

    public DroppedContainerComponentDeserializer(WorldHolder worldHolder) {
        this.worldHolder = worldHolder;
    }

    @Override
    public DroppedContainerComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonObject()) {
            return null;
        }

        JsonObject jsonObject = json.getAsJsonObject();
        String targetType = FormatterContext.asString(jsonObject, "targetDraggingType");
        String droppedHandlerClassName = FormatterContext.asString(jsonObject, "droppedHandler");
        DroppedHandler droppedHandler = HandlerLoader.createHandler(droppedHandlerClassName, DroppedHandler.class);
        droppedHandler.setWorldHolder(worldHolder);

        return new DroppedContainerComponent().droppedHandler(targetType, droppedHandler);
    }
}
