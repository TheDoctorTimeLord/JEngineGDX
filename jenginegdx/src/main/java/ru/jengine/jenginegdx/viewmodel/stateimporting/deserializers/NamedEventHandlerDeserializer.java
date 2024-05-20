package ru.jengine.jenginegdx.viewmodel.stateimporting.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Shared;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.sequence.NamedEventHandler;
import ru.jengine.jsonconverter.formatting.FormatterContext;
import ru.jengine.jsonconverter.serializeprocess.JsonConverterDeserializer;

import java.lang.reflect.Type;

@Bean
@Shared
public class NamedEventHandlerDeserializer implements JsonConverterDeserializer<NamedEventHandler> { //TODO починить HierarchyWalkerUtils с получением параметризированных типов
    @Override
    public NamedEventHandler<?> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (!jsonElement.isJsonObject()) {
            throw new JsonParseException("Expected handler by JSON-object but got: " + jsonElement);
        }

        return context.deserialize(jsonElement, FormatterContext.classPath(jsonElement.getAsJsonObject()));
    }
}
