package ru.jengine.jenginegdx.viewmodel.stateimporting.deserializers;

import com.badlogic.gdx.math.Vector3;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Shared;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.CoordinatesComponent;
import ru.jengine.jenginegdx.utils.JsonUtils;
import ru.jengine.jsonconverter.serializeprocess.JsonConverterDeserializer;

import java.lang.reflect.Type;

import static ru.jengine.jenginegdx.utils.JsonUtils.isNumber;

@Bean
@Shared
public class CoordinatesComponentDeserializer implements JsonConverterDeserializer<CoordinatesComponent> {
    //TODO учитывать различные формы представления данных JSON
    private static final Logger LOG = LoggerFactory.getLogger(CoordinatesComponentDeserializer.class);
    private static final String X = "x";
    private static final String Y = "y";
    private static final String Z = "z";
    private static final String COORDINATES = "coordinates";

    @Override
    public CoordinatesComponent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (!jsonElement.isJsonObject()) {
            return null;
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement coordinates = jsonObject.get(COORDINATES);
        if (coordinates != null) {
            Vector3 vector = context.deserialize(coordinates, Vector3.class);
            return new CoordinatesComponent().coordinates(vector);
        }

        JsonElement x = jsonObject.remove(X);
        JsonElement y = jsonObject.remove(Y);
        JsonElement z = jsonObject.remove(Z);
        if (x == null || y == null) {
            throw new JsonParseException("CoordinatesComponent must have x and y coordinates. Actual: " + jsonObject);
        }
        if (!isNumber(x) || !isNumber(y) || (z != null && !isNumber(z))) {
            throw new JsonParseException(("CoordinatesComponent must have x, y and z as float. x=[%s], y=[%s], z=[%s]")
                    .formatted(x, y, z));
        }
        if (JsonUtils.hasNonInternalFields(jsonObject.keySet())) {
            LOG.warn("CoordinatesComponent has unexpected fields: {}", jsonObject);
        }
        float valX = x.getAsFloat();
        float valY = y.getAsFloat();
        float valZ = z == null ? Float.NEGATIVE_INFINITY : z.getAsFloat();

        return new CoordinatesComponent().coordinates(valX, valY, valZ);
    }
}
