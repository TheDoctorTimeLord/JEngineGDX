package ru.jengine.jenginegdx.viewmodel.stateimporting.formatters;

import com.google.gson.JsonObject;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Shared;
import ru.jengine.jenginegdx.Constants.JsonFormatters.InternalFields;
import ru.jengine.jenginegdx.Constants.JsonFormatters.Priorities;
import ru.jengine.jsonconverter.formatting.FormatterContext;
import ru.jengine.jsonconverter.formatting.JsonFormatter;

import java.util.Set;

@Bean
@Shared
public class ParentJsonFormatter implements JsonFormatter<FormatterContext> {
    private static final Set<String> REQUIRED_FIELDS = Set.of(InternalFields.PARENT);

    @Override
    public Set<String> getRequiredFields() {
        return REQUIRED_FIELDS;
    }

    @Override
    public int getPriority() {
        return Priorities.PARENT_FORMATTER;
    }

    @Override
    public boolean formatJson(JsonObject json, FormatterContext context) {
        context.parent(json, InternalFields.PARENT);
        return true;
    }
}
