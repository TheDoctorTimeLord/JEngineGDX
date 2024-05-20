package ru.jengine.jenginegdx.viewmodel.stateimporting;

import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.google.gson.JsonParseException;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.utils.exceptions.JEngineGdxException;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.sequence.NamedEventHandler;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.systems.EventBus;
import ru.jengine.jenginegdx.viewmodel.ecs.worldholder.EntityPrototype;
import ru.jengine.jenginegdx.viewmodel.ecs.worldholder.WorldHolder;
import ru.jengine.jenginegdx.viewmodel.stateimporting.entityloading.EntitiesContainer;
import ru.jengine.jenginegdx.viewmodel.stateimporting.entityloading.EntityDto;
import ru.jengine.jenginegdx.viewmodel.stateimporting.entityloading.EntityDtoLoader;
import ru.jengine.jenginegdx.viewmodel.stateimporting.linking.EntityComponentsHolder;
import ru.jengine.jenginegdx.viewmodel.stateimporting.linking.EntityLinkingInfo;
import ru.jengine.jsonconverter.resources.ResourceMetadata;

import java.util.Map.Entry;

@Bean
public class WorldStateImporter {
    private final EventBus eventBus;
    private final EntityDtoLoader entityDtoLoader;
    private final EntityToWorldLoader entityToWorldLoader;

    public WorldStateImporter(EventBus eventBus, EntityDtoLoader entityDtoLoader, EntityToWorldLoader entityToWorldLoader)
    {
        this.eventBus = eventBus;
        this.entityDtoLoader = entityDtoLoader;
        this.entityToWorldLoader = entityToWorldLoader;
    }

    public void loadState(WorldHolder worldHolder, ResourceMetadata worldStatePath, boolean needValidate) {
        EntitiesContainer container = entityDtoLoader.loadEntities(worldStatePath);

        worldHolder.clearWorld();
        loadPrototypes(worldHolder, container);
        loadEntities(worldHolder, worldStatePath, container);

        if (needValidate) {
            worldHolder.validateEntitiesStates();
        }

        loadEventHandlers(worldHolder, container);
    }

    private static void loadPrototypes(WorldHolder worldHolder, EntitiesContainer container) {
        EntityLinkingInfo linkingContext = container.getEntityLinkingInfo();

        for (Entry<String, EntityDto> prototype : container.getPrototypes().entrySet()) {
            EntityDto prototypeDefinition = prototype.getValue();

            EntityLinkingInfo prototypeLinkingContext = new EntityLinkingInfo();
            fillPrototypeLinkingInfo(prototypeDefinition, prototypeLinkingContext, linkingContext);
            worldHolder.registerPrototype(
                    prototype.getKey(),
                    new EntityPrototype(prototypeDefinition, prototypeLinkingContext));
        }
    }

    private static void fillPrototypeLinkingInfo(
            EntityDto prototypeDefinition,
            EntityLinkingInfo prototypeLinkingContext,
            EntityLinkingInfo globalLinkingContext)
    {
        EntityComponentsHolder holder = globalLinkingContext.getEntityComponentsHolder(prototypeDefinition.getId());
        if (holder != null) {
            prototypeLinkingContext.registerEntityComponentsHolder(holder);
        }
        else {
            Gdx.app.debug("PROTOTYPE LOADING", "Prototype with entity id [%s] has no links to another entities"
                    .formatted(prototypeDefinition.getId()));
        }

        for (EntityDto childPrototypeDefinition : prototypeDefinition.getChildren()) {
            fillPrototypeLinkingInfo(childPrototypeDefinition, prototypeLinkingContext, globalLinkingContext);
        }
    }

    private void loadEntities(WorldHolder worldHolder, ResourceMetadata worldStatePath, EntitiesContainer container) {
        try {
            entityToWorldLoader.loadEntities(worldHolder.getWorld(), container.getEntityLinkingInfo(), container.getEntities());
        } catch (JEngineGdxException e) {
            throw new JsonParseException("Error when loading entities by path [%s]".formatted(worldStatePath), e);
        }
    }

    private void loadEventHandlers(WorldHolder worldHolder, EntitiesContainer container) {
        World world = worldHolder.getWorld();
        for (NamedEventHandler<?> eventHandler : container.getEventHandlers()) {
            world.inject(eventHandler);
            eventBus.registerNamedHandler(eventHandler);
        }
    }
}
