package ru.jengine.jenginegdx.viewmodel.stateimporting.formatters;

import com.artemis.Component;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.ClassesWith;
import ru.jengine.beancontainer.annotations.Shared;
import ru.jengine.beancontainer.exceptions.ContainerException;
import ru.jengine.jenginegdx.Constants.JsonFormatters.EntitySpecialFields;
import ru.jengine.jenginegdx.Constants.JsonFormatters.InternalFields;
import ru.jengine.jenginegdx.Constants.JsonFormatters.JsonTypes;
import ru.jengine.jenginegdx.Constants.JsonFormatters.Priorities;
import ru.jengine.jenginegdx.utils.RandomUtils;
import ru.jengine.jenginegdx.viewmodel.ecs.ExternalAddable;
import ru.jengine.jenginegdx.viewmodel.stateimporting.WorldEntityLinker;
import ru.jengine.jsonconverter.JsonConverterConstants;
import ru.jengine.jsonconverter.formatting.FormatterContext;
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
public class ComponentJsonFormatter implements JsonFormatter<FormatterContext> {
    private static final Logger LOG = LoggerFactory.getLogger(ComponentJsonFormatter.class);
    private static final Set<String> REQUIRED_FIELD = Set.of(InternalFields.TYPE);
    private final Map<String, Class<? extends Component>> componentClasses;
    private final WorldEntityLinker entityLinker;

    public ComponentJsonFormatter(@ClassesWith(ExternalAddable.class) List<Class<? extends Component>> componentClasses,
            WorldEntityLinker entityLinker)
    {
        this.componentClasses = componentClasses.stream()
                .collect(Collectors.toMap(cls -> cls.getAnnotation(ExternalAddable.class).value(), Function.identity()));
        this.entityLinker = entityLinker;
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
    public boolean formatJson(JsonObject jsonObject, FormatterContext formatterContext) {
        formatterContext.ifType(jsonObject, JsonTypes.ENTITY, () -> {
            String id = initializeId(jsonObject);
            List<String> componentFields = extractComponentFields(jsonObject);
            jsonObject.add("components", convertComponentFields(jsonObject, id, componentFields));
        });
        return true;
    }

    private List<String> extractComponentFields(JsonObject jsonObject) {
        List<String> componentFields = new ArrayList<>();

        boolean hasIncorrectFields = false;
        for (Entry<String, JsonElement> field : jsonObject.entrySet()) {
            String fieldName = field.getKey();
            JsonElement fieldValue = field.getValue();

            boolean isCorrectId = EntitySpecialFields.ID.equals(fieldName) && fieldValue.isJsonPrimitive();
            boolean isCorrectComponent = componentClasses.containsKey(fieldName) && fieldValue.isJsonObject();

            if (!isCorrectId && !isCorrectComponent) {
                hasIncorrectFields = true;
            }
            else {
                componentFields.add(fieldName);
            }
        }
        if (hasIncorrectFields) {
            String unavailableFields = String.join(", ", Sets.difference(jsonObject.keySet(), Sets.newHashSet(componentFields)));
            throw new ContainerException(("JSON is sing ECS entity but properties [%s] are not available. JSON: %s").formatted(unavailableFields, jsonObject));
        }
        return componentFields;
    }

    private static String initializeId(JsonObject object) {
        if (!object.has(EntitySpecialFields.ID)) {
            String tempId = "temp" + RandomUtils.nextInt(0, Integer.MAX_VALUE);
            object.add(EntitySpecialFields.ID, new JsonPrimitive(tempId));
        }
        return object.get(EntitySpecialFields.ID).getAsString();
    }

    private JsonArray convertComponentFields(JsonObject jsonObject, String entityId, List<String> componentFields) {
        JsonArray components = new JsonArray(jsonObject.size());
        for (String field : componentFields) {
            if (EntitySpecialFields.ALL.contains(field)) {
                continue;
            }

            Class<? extends Component> componentClass = componentClasses.get(field);
            JsonObject componentDefinition = jsonObject.remove(field).getAsJsonObject();
            componentDefinition.addProperty(JsonConverterConstants.CLASS_PATH_FIELD, componentClass.getName());
            components.add(componentDefinition);

            entityLinker.registerComponent(entityId, componentClass, componentDefinition);
        }
        return components;
    }
}
