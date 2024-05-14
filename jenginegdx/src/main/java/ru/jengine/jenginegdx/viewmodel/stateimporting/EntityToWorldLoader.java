package ru.jengine.jenginegdx.viewmodel.stateimporting;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.EntityEdit;
import com.artemis.World;
import com.google.gson.JsonParseException;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.utils.exceptions.JEngineGdxException;
import ru.jengine.jenginegdx.viewmodel.ecs.CanBeFilling;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.HierarchyComponent;
import ru.jengine.jenginegdx.viewmodel.stateimporting.entityloading.EntityDto;
import ru.jengine.jenginegdx.viewmodel.stateimporting.linking.EntityLinkingInfo;
import ru.jengine.jenginegdx.viewmodel.stateimporting.linking.LinkableEntityIdMapper;
import ru.jengine.jenginegdx.viewmodel.stateimporting.linking.WorldEntityLinker;

import java.util.List;
import java.util.stream.Stream;

@Bean
public class EntityToWorldLoader {
    private final WorldEntityLinker entityLinker;

    public EntityToWorldLoader(WorldEntityLinker entityLinker) {
        this.entityLinker = entityLinker;
    }

    public void loadEntities(World world, EntityLinkingInfo linkingContext, List<EntityDto> entities) {
        LinkableEntityIdMapper idMapper = new LinkableEntityIdMapper();
        for (EntityDto entity : entities) {
            try {
                loadEntity(world, entity, idMapper);
            } catch (JEngineGdxException e) {
                throw new JEngineGdxException(e.getMessage() + " Entity position: " + entities.indexOf(entity), e);
            }
        }

        List<String> entitiesIds = entities.stream()
                .flatMap(entity -> Stream.concat(Stream.of(entity), entity.getChildren().stream()))
                .map(EntityDto::getId)
                .toList();
        entityLinker.linkEntities(entitiesIds, world, linkingContext, idMapper);
    }

    private int loadEntity(World world, EntityDto entity, LinkableEntityIdMapper idMapper) {
        int entityId = loadEntityOnWorld(world, entity, idMapper);
        loadChildren(world, entity, entityId, idMapper);
        return entityId;
    }

    private void loadChildren(World world, EntityDto parentEntity, int parentId, LinkableEntityIdMapper idMapper) {
        ComponentMapper<HierarchyComponent> hierarchyMapper = world.getMapper(HierarchyComponent.class);

        if (!parentEntity.getChildren().isEmpty()) {
            for (EntityDto child : parentEntity.getChildren()) {
                try {
                    int childId = loadEntity(world, child, idMapper);
                    getHierarchy(parentId, hierarchyMapper).childrenId.add(childId);
                    getHierarchy(childId, hierarchyMapper).parentId = parentId;
                } catch (JsonParseException e) {
                    throw new JEngineGdxException(e.getMessage() + " Child entity by " + child.getChildren().indexOf(child), e);
                }
            }
        }
    }

    private static int loadEntityOnWorld(World world, EntityDto entity, LinkableEntityIdMapper idMapper) {
        int entityId = world.create();
        EntityEdit entityEdit = world.edit(entityId);
        idMapper.registerEntity(entity.getId(), entityId);

        for (Component component : entity.getComponents()) {
            Component addedComponent = entityEdit.create(component.getClass());
            if (addedComponent instanceof CanBeFilling<?> canBeFilled && !canBeFilled.fill(component)) {
                throw new JEngineGdxException("Entity [%s] has incorrect state of component [%s]."
                        .formatted(entity.getId(), addedComponent.getClass()));
            }
        }
        return entityId;
    }

    private static HierarchyComponent getHierarchy(int entityId, ComponentMapper<HierarchyComponent> mapper) {
        return mapper.has(entityId) ? mapper.get(entityId) : mapper.create(entityId);
    }
}
