package ru.jengine.jenginegdx.viewmodel.ecs.validations.onstartvalidators.standardvalidators;

import com.artemis.World;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Shared;
import ru.jengine.jenginegdx.viewmodel.ecs.location.systems.AbsoluteCoordinatesSystem;
import ru.jengine.jenginegdx.viewmodel.ecs.validations.onstartvalidators.WorldValidator;
import ru.jengine.jenginegdx.viewmodel.ecs.worldholder.WorldHolder;

@Bean
@Shared
public class LocalToAbsoluteCoordinatesValidator implements WorldValidator {
    @Override
    public void validate(WorldHolder worldHolder) {
        World world = worldHolder.getWorld();
        world.getSystem(AbsoluteCoordinatesSystem.class).process();
    }
}
