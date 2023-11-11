package ru.jengine.jenginegdx.container.wrappers;

import com.artemis.BaseSystem;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.beancontainer.annotations.PreDestroy;
import ru.jengine.jenginegdx.Constants.UpdatableSystemOrder;
import ru.jengine.jenginegdx.container.Updatable;

import java.util.List;

@Bean
@Order(UpdatableSystemOrder.ECS_WORLD)
public class WorldWrapper implements Updatable {
    private final World world;

    public WorldWrapper(List<BaseSystem> ecsSystems) {
        WorldConfiguration config = new WorldConfiguration();

        for (BaseSystem ecsSystem : ecsSystems) {
            config.setSystem(ecsSystem);
        }

        this.world = new World(config);
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void update() {
        world.process();
    }

    @PreDestroy
    private void dispose() {
        world.dispose();
    }
}
