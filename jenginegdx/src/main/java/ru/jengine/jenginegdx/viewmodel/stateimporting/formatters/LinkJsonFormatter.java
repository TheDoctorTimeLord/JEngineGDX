package ru.jengine.jenginegdx.viewmodel.stateimporting.formatters;

import com.google.gson.JsonObject;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Shared;
import ru.jengine.jenginegdx.Constants.JsonFormatters.Priorities;
import ru.jengine.jsonconverter.formatting.FormatterContext;
import ru.jengine.jsonconverter.formatting.JsonFormatter;

import java.util.List;
import java.util.Set;

@Bean
@Shared
public class LinkJsonFormatter implements JsonFormatter<FormatterContext> {
    @Override
    public Set<String> getRequiredFields() {
        return Set.of();
    }

    @Override
    public int getPriority() {
        return Priorities.LINK_FORMATTER;
    }

    @Override
    public boolean formatJson(JsonObject json, FormatterContext context) {
        List<String> links = context.fieldsWithLink(json);
        for (String link : links) {
            context.link(json, link);
        }
        return true;
    }
}
