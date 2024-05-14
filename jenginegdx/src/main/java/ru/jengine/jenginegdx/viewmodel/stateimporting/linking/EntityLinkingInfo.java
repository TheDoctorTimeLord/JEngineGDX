package ru.jengine.jenginegdx.viewmodel.stateimporting.linking;

import com.artemis.Component;
import com.artemis.annotations.EntityId;
import com.artemis.utils.IntBag;
import com.google.gson.*;
import org.reflections.ReflectionUtils;
import ru.jengine.beancontainer.exceptions.ContainerException;
import ru.jengine.jenginegdx.Constants.JsonFormatters;
import ru.jengine.jenginegdx.Constants.Linking;
import ru.jengine.jenginegdx.utils.JsonUtils;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.EntityLink;
import ru.jengine.jenginegdx.viewmodel.stateimporting.linking.LinkField.MultiLink;
import ru.jengine.jenginegdx.viewmodel.stateimporting.linking.LinkField.SingleLink;
import ru.jengine.utils.AnnotationUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EntityLinkingInfo {
    private static final Map<Class<? extends Component>, Field[]> componentsWithLinks = new HashMap<>();
    private final Map<String, EntityComponentsHolder> entityComponentsHolders = new HashMap<>();

    public void registerEntityComponentsHolder(EntityComponentsHolder holder) {
        entityComponentsHolders.put(holder.getEntityId(), holder);
    }

    public void registerComponent(String entityId, Class<? extends Component> componentClass, JsonObject componentFilling)
    {
        Field[] linkFields = componentsWithLinks.computeIfAbsent(componentClass, EntityLinkingInfo::findLinks);

        if (linkFields.length == 0) {
            return;
        }

        LinkField[] fieldsWithLink = Arrays.stream(linkFields)
                .map(field -> convertField(field, componentFilling))
                .toArray(LinkField[]::new);

        entityComponentsHolders.computeIfAbsent(entityId, EntityComponentsHolder::new)
                .registerComponent(componentClass, fieldsWithLink);
    }

    @SuppressWarnings("unchecked")
    private static Field[] findLinks(Class<?> componentClass) {
        return ReflectionUtils
                .getAllFields(componentClass, field -> AnnotationUtils.isAnnotationPresent(field, EntityId.class)
                        || AnnotationUtils.isAnnotationPresent(field, EntityLink.class))
                .toArray(Field[]::new);
    }

    private static LinkField convertField(Field field, JsonObject componentFilling) {
        String fieldName = field.getName();
        Class<?> fieldType = field.getType();
        JsonElement idField = componentFilling.get(fieldName);

        if (isType(fieldType, Integer.class, int.class) && (JsonUtils.isNull(idField) || JsonUtils.isString(idField))) {
            componentFilling.addProperty(fieldName, Linking.LINK_TO_NULL);
            return new SingleLink(fieldName, JsonUtils.isNull(idField) ? JsonFormatters.EMPTY_LINK : idField.getAsString());
        }
        if (isType(fieldType, IntBag.class) && (JsonUtils.isNull(idField) || idField.isJsonArray())) {
            componentFilling.add(fieldName, JsonNull.INSTANCE);
            return new MultiLink(fieldName, convertToStringArray(idField));
        }
        throw new JsonParseException("Field [%s] with EntityId is incorrect type [%s] or value [%s] in [%s]"
                .formatted(fieldName, fieldType, idField, componentFilling));
    }

    private static boolean isType(Class<?> checkedType, Class<?>... variants) { //TODO вынести в утилитарные классы
        for (Class<?> variant : variants) {
            if (variant.equals(checkedType)) {
                return true;
            }
        }
        return false;
    }

    private static String[] convertToStringArray(JsonElement jsonElement) {
        if (JsonUtils.isNull(jsonElement)) return new String[0];

        JsonArray jsonArray = jsonElement.getAsJsonArray();
        String[] array = new String[jsonArray.size()];
        for (int i = 0; i < array.length; i++) {
            JsonElement element = jsonArray.get(i);
            if (!JsonUtils.isString(element)) {
                throw new ContainerException("Array must have string id only: " + jsonArray);
            }
            array[i] = element.getAsString();
        }
        return array;
    }

    @Nullable
    public EntityComponentsHolder getEntityComponentsHolder(String entityId) {
        return entityComponentsHolders.get(entityId);
    }

    public Iterable<EntityComponentsHolder> getEntityComponentsHolders() {
        return entityComponentsHolders.values();
    }
}
