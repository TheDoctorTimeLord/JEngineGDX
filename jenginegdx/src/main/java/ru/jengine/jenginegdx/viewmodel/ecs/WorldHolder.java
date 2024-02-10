package ru.jengine.jenginegdx.viewmodel.ecs;

import com.artemis.BaseSystem;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.PreDestroy;

import java.util.List;
import java.util.Set;

@Bean
public class WorldHolder {
    private final World world;

    public WorldHolder(List<BaseSystem> ecsSystems) {
        WorldConfiguration config = new WorldConfiguration();

        for (BaseSystem ecsSystem : ecsSystems) {
            config.setSystem(ecsSystem);
        }
        config.setAlwaysDelayComponentRemoval(true);

        this.world = new World(config);
    }

    public void setActiveSystems(Set<Class<? extends BaseSystem>> activeSystems) {
        for (BaseSystem system : getWorld().getSystems()) {
            boolean isEnable = activeSystems.contains(system.getClass());
            system.setEnabled(isEnable);
        }
    }

    public World getWorld() {
        return world;
    }

    public void update() {
        world.process();
    }

    @PreDestroy
    private void dispose() {
        world.dispose();
    }
}
