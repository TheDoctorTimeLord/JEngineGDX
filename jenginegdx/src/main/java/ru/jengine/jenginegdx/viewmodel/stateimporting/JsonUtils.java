package ru.jengine.jenginegdx.viewmodel.stateimporting;

import com.google.common.collect.Sets;
import ru.jengine.jenginegdx.Constants.JsonFormatters.InternalFields;

import java.util.Set;

public class JsonUtils {
    public static boolean hasNonInternalFields(Set<String> jsonFields) {
        return !Sets.difference(jsonFields, InternalFields.ALL).isEmpty();
    }
}
