package ru.jengine.jenginegdx.viewmodel.stateimporting.formatters;

import com.artemis.Component;
import com.google.common.collect.Sets;
import com.google.gson.*;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.ClassesWith;
import ru.jengine.beancontainer.annotations.Shared;
import ru.jengine.jenginegdx.Constants.JsonFormatters.EntitySpecialFields;
import ru.jengine.jenginegdx.Constants.JsonFormatters.InternalFields;
import ru.jengine.jenginegdx.Constants.JsonFormatters.JsonTypes;
import ru.jengine.jenginegdx.Constants.JsonFormatters.Priorities;
import ru.jengine.jenginegdx.utils.JsonUtils;
import ru.jengine.jenginegdx.utils.RandomUtils;
import ru.jengine.jenginegdx.viewmodel.ecs.ExternalAddable;
import ru.jengine.jenginegdx.viewmodel.stateimporting.entityloading.LinkingFormatterContext;
import ru.jengine.jsonconverter.JsonConverterConstants;
import ru.jengine.jsonconverter.formatting.JsonFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Bean
@Shared
public class ComponentJsonFormatter implements JsonFormatter<LinkingFormatterContext> {
    private static final Set<String> REQUIRED_FIELD = Set.of(InternalFields.TYPE);
    private final Map<String, Class<? extends Component>> componentClasses;

    public ComponentJsonFormatter(@ClassesWith(ExternalAddable.class) List<Class<? extends Component>> componentClasses)
    {
        this.componentClasses = componentClasses.stream()
                .collect(Collectors.toMap(cls -> cls.getAnnotation(ExternalAddable.class).value(), Function.identity()));
    }

    @Override
    public Set<String> getRequiredFields() {
        return REQUIRED_FIELD;
    }

    @Override
    public int getPriority() {
        return Priorities.COMPONENT_FORMATTER;
    }

    @Override
    public boolean formatJson(JsonObject jsonObject, LinkingFormatterContext context) {
        context.ifType(jsonObject, JsonTypes.ENTITY, () -> {
            String id = initializeId(jsonObject);
            List<String> componentFields = extractComponentFields(jsonObject);
            jsonObject.add("components", convertComponentFields(jsonObject, id, componentFields, context));
        });
        return true;
    }

    private static String initializeId(JsonObject object) {
        if (object.has(EntitySpecialFields.ID)) {
            if (!JsonUtils.isString(object.get(EntitySpecialFields.ID))) {
                throw new JsonParseException("Field [%s] must be string. JSON: %s".formatted(EntitySpecialFields.ID, object));
            }
        }
        else {
            String tempId = "temp" + RandomUtils.nextInt(0, Integer.MAX_VALUE);
            object.add(EntitySpecialFields.ID, new JsonPrimitive(tempId));
        }
        return object.get(EntitySpecialFields.ID).getAsString();
    }

    private List<String> extractComponentFields(JsonObject jsonObject) {
        List<String> componentFields = new ArrayList<>();

        boolean hasIncorrectFields = false;
        for (Entry<String, JsonElement> field : jsonObject.entrySet()) {
            String fieldName = field.getKey();
            if (isInternalField(fieldName) || isSpecialField(fieldName) || isCorrectComponent(field)) {
                componentFields.add(fieldName);
            }
            else {
                hasIncorrectFields = true;
            }
        }
        if (hasIncorrectFields) {
            String unavailableFields = String.join(", ", Sets.difference(jsonObject.keySet(), Sets.newHashSet(componentFields)));
            throw new JsonParseException(("JSON is sing ECS entity but properties [%s] are not available. JSON: %s").formatted(unavailableFields, jsonObject));
        }
        return componentFields;
    }

    private JsonArray convertComponentFields(JsonObject jsonObject, String entityId, List<String> componentFields,
            LinkingFormatterContext context)
    {
        JsonArray components = new JsonArray(jsonObject.size());
        for (String field : componentFields) {
            if (isInternalField(field) || isSpecialField(field)) {
                continue;
            }

            Class<? extends Component> componentClass = componentClasses.get(field);
            JsonObject componentDefinition = jsonObject.remove(field).getAsJsonObject();
            componentDefinition.addProperty(JsonConverterConstants.CLASS_PATH_FIELD, componentClass.getName());
            components.add(componentDefinition);

            context.registerLinks(entityId, componentClass, componentDefinition);
        }
        return components;
    }

    private static boolean isInternalField(String field) {
        return InternalFields.ALL.contains(field);
    }

    private static boolean isSpecialField(String field) {
        return EntitySpecialFields.ALL.contains(field);
    }

    private boolean isCorrectComponent(Map.Entry<String, JsonElement> field) {
        return componentClasses.containsKey(field.getKey()) && field.getValue().isJsonObject();
    }
}
