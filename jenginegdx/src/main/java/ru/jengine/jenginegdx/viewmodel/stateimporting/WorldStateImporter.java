package ru.jengine.jenginegdx.viewmodel.stateimporting;

import com.artemis.Component;
import com.artemis.EntityEdit;
import com.artemis.World;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.exceptions.ContainerException;
import ru.jengine.jenginegdx.viewmodel.ecs.CanBeFilling;
import ru.jengine.jenginegdx.viewmodel.stateimporting.dto.EntityJsonDto;
import ru.jengine.jenginegdx.viewmodel.stateimporting.dto.UIEntitiesContainer;
import ru.jengine.jsonconverter.JsonConverter;
import ru.jengine.jsonconverter.resources.ResourceMetadata;

@Bean
public class WorldStateImporter {
    private final JsonConverter jsonConverter;
    private final WorldEntityLinker entityLinker;

    public WorldStateImporter(JsonConverter jsonConverter, WorldEntityLinker entityLinker) {
        this.jsonConverter = jsonConverter;
        this.entityLinker = entityLinker;
    }

    public void loadState(World world, ResourceMetadata worldStatePath) {
        UIEntitiesContainer container = jsonConverter.convertFromJson(worldStatePath, UIEntitiesContainer.class);
        for (EntityJsonDto entity : container.getEntities()) {
            if (entity.getId() == null || entity.getComponents() == null) {
                throw new ContainerException("Incorrect converting entity. Entity position: " + container.getEntities().indexOf(entity));
            }

            EntityEdit entityEdit = world.createEntity().edit();
            entityLinker.registerEntity(entity.getId(), entityEdit.getEntityId());

            for (Component component : entity.getComponents()) {
                Component addedComponent = entityEdit.create(component.getClass());
                if (addedComponent instanceof CanBeFilling<?> canBeFilled && !canBeFilled.fill(component)) {
                    throw new ContainerException("Entity [%s] has incorrect state of component [%s]".formatted(entity.getId(), addedComponent.getClass()));
                }
            }
        }
        entityLinker.linkEntities(world);
    }
}
