package ru.jengine.jenginegdx.viewmodel.stateimporting.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Shared;
import ru.jengine.jenginegdx.viewmodel.ecs.input.components.UserEventHandlingComponent;
import ru.jengine.jsonconverter.serializeprocess.JsonConverterDeserializer;

import java.lang.reflect.Type;
import java.util.Map.Entry;

@Bean
@Shared
public class UserEventHandlingComponentDeserializer implements JsonConverterDeserializer<UserEventHandlingComponent> {
    @Override
    public UserEventHandlingComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonObject()) {
            return null;
        }
        JsonObject object = json.getAsJsonObject();

        UserEventHandlingComponent userEventHandling = new UserEventHandlingComponent();
        for (Entry<String, JsonElement> entry : object.entrySet()) {
            JsonElement mapping = entry.getValue();
            if (!mapping.isJsonPrimitive() && !mapping.getAsJsonPrimitive().isString()) {
                throw new JsonParseException("JSON of user events must contain mappings with format ['from event' : 'to event] but actual [%s]".formatted(json));
            }
            userEventHandling.addHandling(entry.getKey(), mapping.getAsString());
        }
        return userEventHandling;
    }
}
