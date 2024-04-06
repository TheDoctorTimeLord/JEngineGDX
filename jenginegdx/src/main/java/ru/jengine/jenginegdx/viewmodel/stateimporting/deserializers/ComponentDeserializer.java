package ru.jengine.jenginegdx.viewmodel.stateimporting.deserializers;

import com.artemis.Component;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Shared;
import ru.jengine.jsonconverter.formatting.FormatterContext;
import ru.jengine.jsonconverter.serializeprocess.JsonConverterDeserializer;

import java.lang.reflect.Type;

@Bean
@Shared
public class ComponentDeserializer implements JsonConverterDeserializer<Component> {
    @Override
    public Component deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException
    {
        if (!jsonElement.isJsonObject()) {
            throw new JsonParseException("Component must be JsonObject. But actual: " + jsonElement);
        }

        return context.deserialize(jsonElement, FormatterContext.classPath(jsonElement.getAsJsonObject()));
    }
}
