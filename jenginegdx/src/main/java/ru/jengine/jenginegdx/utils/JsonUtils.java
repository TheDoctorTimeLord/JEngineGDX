package ru.jengine.jenginegdx.utils;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import ru.jengine.jenginegdx.Constants.JsonFormatters.InternalFields;

import javax.annotation.Nullable;
import java.util.Set;

public class JsonUtils {
    public static boolean hasNonInternalFields(Set<String> jsonFields) {
        return !Sets.difference(jsonFields, InternalFields.ALL).isEmpty();
    }

    public static boolean isString(JsonElement element) {
        return element.isJsonPrimitive() && element.getAsJsonPrimitive().isString();
    }

    public static boolean isNumber(JsonElement element) {
        return element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber();
    }

    public static boolean isBoolean(JsonElement element) {
        return element.isJsonPrimitive() && element.getAsJsonPrimitive().isBoolean();
    }

    public static boolean isNull(@Nullable JsonElement idField) {
        return idField == null || idField.isJsonNull();
    }
}
