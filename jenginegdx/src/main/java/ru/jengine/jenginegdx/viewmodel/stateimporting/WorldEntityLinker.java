package ru.jengine.jenginegdx.viewmodel.stateimporting;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.annotations.EntityId;
import com.artemis.utils.IntBag;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import org.reflections.ReflectionUtils;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.exceptions.ContainerException;
import ru.jengine.beancontainer.utils.AnnotationUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@Bean
public class WorldEntityLinker {
    private final Map<Class<? extends Component>, Field[]> componentsWithLinks = new HashMap<>();
    private final Map<String, EntityHolder> entityHolders = new HashMap<>();
    private final Map<String, Integer> entitiesById = new HashMap<>();

    @SuppressWarnings("unchecked")
    public void registerComponent(String entityId, Class<? extends Component> componentClass, JsonObject componentFilling) {
        Field[] linkFields = componentsWithLinks.computeIfAbsent(
                componentClass,
                cls -> ReflectionUtils
                        .getAllFields(componentClass, field -> AnnotationUtils.isAnnotationPresent(field, EntityId.class))
                        .toArray(Field[]::new)
        );
        if (linkFields.length == 0) {
            return;
        }

        LinkField[] fieldsWithLink = Arrays.stream(linkFields)
                .map(field -> convertField(field, componentFilling))
                .toArray(LinkField[]::new);

        EntityHolder entityHolder = entityHolders.computeIfAbsent(entityId, id -> new EntityHolder());
        entityHolder.components.computeIfAbsent(componentClass, cls -> new ComponentHolder(fieldsWithLink));
    }

    private static LinkField convertField(Field field, JsonObject componentFilling) {
        String fieldName = field.getName();
        Class<?> fieldType = field.getType();
        JsonElement idField = componentFilling.get(fieldName);

        if ((Integer.class.equals(fieldType) || int.class.equals(fieldType)) && idField.isJsonPrimitive()) {
            componentFilling.addProperty(fieldName, -1);
            return new SingleLink(fieldName, idField.getAsString());
        }
        if (IntBag.class.equals(fieldType) && idField.isJsonArray()) {
            componentFilling.add(fieldName, JsonNull.INSTANCE);
            return new MultiLink(fieldName, convertToStringArray(idField.getAsJsonArray()));
        }
        throw new ContainerException("Field [%s] with EntityId is incorrect type [%s] or value [%s] in [%s]"
                .formatted(fieldName, fieldType, idField, componentFilling));
    }

    private static String[] convertToStringArray(JsonArray jsonArray) {
        String[] array = new String[jsonArray.size()];
        for (int i = 0; i < array.length; i++) {
            JsonElement element = jsonArray.get(i);
            if (element.isJsonPrimitive()) {
                array[i] = element.getAsString();
            } else {
                throw new ContainerException("Array must have string id only: " + jsonArray);
            }
        }
        return array;
    }

    public void registerEntity(String idInJson, int idInWorld) {
        entitiesById.put(idInJson, idInWorld);
    }

    public void linkEntities(World world) {
        for (Entry<String, EntityHolder> entry : entityHolders.entrySet()) {
            Entity entity = world.getEntity(entitiesById.get(entry.getKey()));
            for (Entry<Class<? extends Component>, ComponentHolder> componentEntry : entry.getValue().components.entrySet()) {
                Component component = entity.getComponent(componentEntry.getKey());
                for (LinkField field : componentEntry.getValue().fields) {
                    linkComponent(component, field);
                }
            }
        }
        entityHolders.clear();
        entitiesById.clear();
    }

    @SuppressWarnings("java:S3011")
    private void linkComponent(Component component, LinkField field) {
        try {
            Field linkingField = component.getClass().getField(field.field());
            if (field instanceof SingleLink single) {
                int link = entitiesById.get(single.id);
                linkingField.setInt(component, link);
            } else if (field instanceof MultiLink multi) {
                IntBag links = Arrays.stream(multi.ids)
                        .map(entitiesById::get)
                        .collect(IntBag::new, IntBag::add, IntBag::addAll);
                linkingField.set(component, links);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ContainerException("Error when setting field [%s] from [%s]".formatted(field.field(), component), e);
        }
    }

    private static class EntityHolder {
        private final Map<Class<? extends Component>, ComponentHolder> components = new HashMap<>();
    }
    @SuppressWarnings("java:S6218")
    private record ComponentHolder(LinkField[] fields) { }
    private interface LinkField {
        String field();
    }
    private record SingleLink(String field, String id) implements LinkField { }
    @SuppressWarnings("java:S6218")
    private record MultiLink(String field, String[] ids) implements LinkField { }
}
