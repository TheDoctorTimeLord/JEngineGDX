package ru.jengine.jenginegdx.viewmodel.stateimporting.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Shared;
import ru.jengine.jenginegdx.viewmodel.ecs.focus.FocusLoseHandler;
import ru.jengine.jenginegdx.viewmodel.ecs.focus.components.FocusSettingsComponent;
import ru.jengine.jsonconverter.formatting.FormatterContext;
import ru.jengine.jsonconverter.serializeprocess.JsonConverterDeserializer;

import java.lang.reflect.Type;

@Bean
@Shared
public class FocusSettingsComponentDeserializer implements JsonConverterDeserializer<FocusSettingsComponent> {
    @Override
    public FocusSettingsComponent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (!jsonElement.isJsonObject()) {
            return null;
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String focusLoseHandlerClass = FormatterContext.asString(jsonObject, "focusLoseHandler");
        FocusLoseHandler handler = HandlerLoader.createHandler(focusLoseHandlerClass, FocusLoseHandler.class);
        return new FocusSettingsComponent().focusLoseHandler(handler);
    }
}
