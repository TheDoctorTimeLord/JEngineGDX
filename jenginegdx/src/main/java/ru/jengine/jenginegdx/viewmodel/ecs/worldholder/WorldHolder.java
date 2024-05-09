package ru.jengine.jenginegdx.viewmodel.ecs.worldholder;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.link.EntityLinkManager;
import ru.jengine.beancontainer.annotations.Api;
import ru.jengine.beancontainer.annotations.Api.ApiElement;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.PreDestroy;
import ru.jengine.jenginegdx.utils.IntBagUtils;
import ru.jengine.jenginegdx.utils.exceptions.JEngineGdxException;
import ru.jengine.jenginegdx.viewmodel.ecs.validations.onstartvalidators.WorldValidationManager;
import ru.jengine.jenginegdx.viewmodel.stateimporting.EntityToWorldLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Bean
public class WorldHolder {
    private final World world;
    private final EntityToWorldLoader entityToWorldLoader;
    private final WorldValidationManager worldValidationManager;
    private final BatchUpdatableInvocationStrategy updatableInvocationStrategy;
    private final Map<String, EntityPrototype> prototypes = new HashMap<>();

    @Api(@ApiElement(index = 0, message = "Add bean extends 'WorldSystemsHolder' with ECS systems list."))
    public WorldHolder(WorldSystemsHolder ecsSystems, EntityToWorldLoader entityToWorldLoader,
            WorldValidationManager worldValidationManager) {
        this.entityToWorldLoader = entityToWorldLoader;
        this.worldValidationManager = worldValidationManager;

        WorldConfiguration config = new WorldConfiguration();
        config.setSystem(new EntityLinkManager());

        for (BaseSystem ecsSystem : ecsSystems.getSystems()) {
            config.setSystem(ecsSystem);
        }
        config.setAlwaysDelayComponentRemoval(true);
        config.setInvocationStrategy(updatableInvocationStrategy = new BatchUpdatableInvocationStrategy());

        this.world = new World(config);
    }

    public void setActiveSystems(Set<Class<? extends BaseSystem>> activeSystems) {
        for (BaseSystem system : getWorld().getSystems()) {
            boolean isEnable = activeSystems.contains(system.getClass());
            system.setEnabled(isEnable);
        }
    }

    public void registerPrototype(String prototypeId, EntityPrototype prototype) {
        prototypes.put(prototypeId, prototype);
    }

    public void loadPrototype(String prototypeId) {
        EntityPrototype prototype = prototypes.get(prototypeId);
        if (prototype == null) {
            throw new JEngineGdxException("Prototype by id [%s] is not found".formatted(prototypeId));
        }

        entityToWorldLoader.loadEntities(world, prototype.linkingContext(), List.of(prototype.prototypeDefinition()));
    }

    public void validateEntitiesStates() {
        updatableInvocationStrategy.updateBatch();
        worldValidationManager.validate(this);
    }

    public World getWorld() {
        return world;
    }

    public void update() {
        world.process();
    }

    public void clearWorld() {
        IntBagUtils.forEach(world.getAspectSubscriptionManager().get(Aspect.all()).getEntities(), world::delete);
        world.getEntityManager().reset();

        prototypes.clear();
    }

    @PreDestroy
    private void dispose() {
        world.dispose();
        prototypes.clear();
    }
}
