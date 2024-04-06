package ru.jengine.jenginegdx.viewmodel.stateimporting.deserializers;

import com.badlogic.gdx.graphics.Texture;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Shared;
import ru.jengine.jenginegdx.view.TextureStorage;
import ru.jengine.jenginegdx.view.texture.TextureComponent;
import ru.jengine.jsonconverter.serializeprocess.JsonConverterDeserializer;

import java.lang.reflect.Type;

@Bean
@Shared
public class TextureComponentDeserializer implements JsonConverterDeserializer<TextureComponent> {
    private static final Logger LOG = LoggerFactory.getLogger(TextureComponentDeserializer.class);

    private final TextureStorage textureStorage;

    public TextureComponentDeserializer(TextureStorage textureStorage) {
        this.textureStorage = textureStorage;
    }

    @Override
    public TextureComponent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (!jsonElement.isJsonObject()) {
            LOG.error("Texture component must be JSON object but actual is null");
            return null;
        }

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement texture = jsonObject.get("texture");
        if (texture == null || !texture.isJsonPrimitive()) {
            LOG.error("Texture component must have field 'texture' as String but actual is {}", texture);
            return null;
        }

        Texture actualTexture = textureStorage.get(texture.getAsString());
        return new TextureComponent().texture(actualTexture);
    }
}
