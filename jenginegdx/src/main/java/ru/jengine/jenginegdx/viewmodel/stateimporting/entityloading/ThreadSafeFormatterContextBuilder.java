package ru.jengine.jenginegdx.viewmodel.stateimporting.entityloading;

import ru.jengine.jenginegdx.viewmodel.stateimporting.linking.EntityLinkingInfo;
import ru.jengine.jsonconverter.formatting.FormatterContextBuilder;
import ru.jengine.jsonconverter.formatting.JsonFormatterManager;
import ru.jengine.jsonconverter.linking.JsonLinker;
import ru.jengine.jsonconverter.resources.JsonLoader;

public class ThreadSafeFormatterContextBuilder implements FormatterContextBuilder<LinkingFormatterContext> {
    private final ThreadLocal<EntityLinkingInfo> workedContexts = new ThreadLocal<>();
    //TODO избавиться от тредлокала, добавив механизм маппинга FormatterContext

    public void start(EntityLinkingInfo info) {
        workedContexts.set(info);
    }

    @Override
    public LinkingFormatterContext getContext(JsonLoader jsonLoader, JsonLinker jsonLinker, JsonFormatterManager jsonFormatterManager) {
        return new LinkingFormatterContext(jsonLoader, jsonLinker, jsonFormatterManager, workedContexts.get());
    }

    public EntityLinkingInfo stop() {
        EntityLinkingInfo info = workedContexts.get();
        workedContexts.remove();
        return info;
    }
}
