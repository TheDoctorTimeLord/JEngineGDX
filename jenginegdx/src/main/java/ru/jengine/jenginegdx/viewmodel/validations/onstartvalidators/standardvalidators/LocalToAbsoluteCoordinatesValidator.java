package ru.jengine.jenginegdx.viewmodel.validations.onstartvalidators.standardvalidators;

import com.artemis.World;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.beancontainer.annotations.Shared;
import ru.jengine.jenginegdx.viewmodel.ecs.location.systems.AbsoluteCoordinatesSystem;
import ru.jengine.jenginegdx.viewmodel.validations.onstartvalidators.WorldValidator;
import ru.jengine.jenginegdx.viewmodel.ecs.worldholder.WorldHolder;

@Bean
@Shared
@Order(200)
public class LocalToAbsoluteCoordinatesValidator implements WorldValidator {
    @Override
    public void validate(WorldHolder worldHolder) {
        World world = worldHolder.getWorld();
        world.getSystem(AbsoluteCoordinatesSystem.class).process();
    }
}
