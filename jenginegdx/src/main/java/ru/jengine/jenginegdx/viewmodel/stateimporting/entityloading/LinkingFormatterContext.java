package ru.jengine.jenginegdx.viewmodel.stateimporting.entityloading;

import com.artemis.Component;
import com.google.gson.JsonObject;
import ru.jengine.jenginegdx.viewmodel.stateimporting.linking.EntityLinkingInfo;
import ru.jengine.jsonconverter.formatting.FormatterContext;
import ru.jengine.jsonconverter.formatting.JsonFormatterManager;
import ru.jengine.jsonconverter.linking.JsonLinker;
import ru.jengine.jsonconverter.resources.JsonLoader;

public class LinkingFormatterContext extends FormatterContext {
    private final EntityLinkingInfo entityLinkingInfo;

    public LinkingFormatterContext(JsonLoader jsonLoader, JsonLinker jsonLinker,
            JsonFormatterManager jsonFormatterManager, EntityLinkingInfo entityLinkingInfo)
    {
        super(jsonLoader, jsonLinker, jsonFormatterManager);
        this.entityLinkingInfo = entityLinkingInfo;
    }

    public void registerLinks(String ownerId, Class<? extends Component> componentClass, JsonObject componentDefinition) {
        entityLinkingInfo.registerComponent(ownerId, componentClass, componentDefinition);
    }
}
