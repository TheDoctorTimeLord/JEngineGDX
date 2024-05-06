package ru.jengine.jenginegdx.viewmodel.stateimporting.entityloading;

import com.google.gson.JsonParseException;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.viewmodel.stateimporting.linking.EntityLinkingInfo;
import ru.jengine.jsonconverter.JsonConverter;
import ru.jengine.jsonconverter.JsonConverterImpl;
import ru.jengine.jsonconverter.formatting.JsonFormatterManagerImpl;
import ru.jengine.jsonconverter.resources.ResourceMetadata;

import java.util.Map.Entry;

@Bean
public class EntityDtoLoader {
    private final JsonConverter jsonConverter;
    private final ThreadSafeFormatterContextBuilder contextBuilder;

    public EntityDtoLoader(JsonConverterImpl jsonConverter, JsonFormatterManagerImpl formatterManager) {
        this.jsonConverter = jsonConverter;

        formatterManager.setFormatterContextStrategy(contextBuilder = new ThreadSafeFormatterContextBuilder());
    }

    public EntitiesContainer loadEntities(ResourceMetadata path) { //TODO не добавлять служебные атрибуты при #parent
        contextBuilder.start(new EntityLinkingInfo()); //TODO получать данные о линкинге из-вне
        EntitiesContainer container = jsonConverter.convertFromJson(path, EntitiesContainer.class);
        container.setEntityLinkingInfo(contextBuilder.stop());

        validateContainer(container);
        return container;
    }

    private static void validateContainer(EntitiesContainer container) {
        for (EntityDto entity : container.getEntities()) {
            if (entity.getId() == null) {
               throw new JsonParseException("The entity on position %s doesn't have id"
                       .formatted(container.getEntities().indexOf(entity)));
            }
            if (entity.getComponents() == null) {
                throw new JsonParseException("The entity with id [%s] was loaded without components"
                        .formatted(entity.getId()));
            }
        }

        for (Entry<String, EntityDto> prototype : container.getPrototypes().entrySet()) {
            String prototypeId = prototype.getKey();
            EntityDto prototypeDefinition = prototype.getValue();

            if (prototypeDefinition.getId() == null) {
                throw new JsonParseException("The prototype [%s] doesn't have entity id".formatted(prototypeId));
            }
            if (prototypeDefinition.getComponents() == null) {
                throw new JsonParseException("The prototype [%s] was loaded without components".formatted(prototypeId));
            }
        }
    }
}
