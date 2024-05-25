package ru.jengine.jenginegdx.viewmodel.validations.onstartvalidators.standardvalidators;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.World;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.beancontainer.annotations.Shared;
import ru.jengine.jenginegdx.utils.IntBagUtils;
import ru.jengine.jenginegdx.viewmodel.ecs.focus.components.FocusSettingsComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.focus.handlers.DefaultFocusLoseHandler;
import ru.jengine.jenginegdx.viewmodel.ecs.input.components.InputBoundComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.worldholder.WorldHolder;
import ru.jengine.jenginegdx.viewmodel.validations.onstartvalidators.WorldValidator;

@Bean
@Shared
@Order(400)
public class FocusValidator implements WorldValidator {
    private final DefaultFocusLoseHandler defaultHandler = new DefaultFocusLoseHandler();

    @Override
    public void validate(WorldHolder worldHolder) {
        World world = worldHolder.getWorld();
        ComponentMapper<FocusSettingsComponent> focusSettingsComponentMapper =
                world.getMapper(FocusSettingsComponent.class);
        EntitySubscription boundEntitiesSubscription =
                world.getAspectSubscriptionManager().get(Aspect.all(InputBoundComponent.class));

        IntBagUtils.forEach(boundEntitiesSubscription.getEntities(), entityId -> {
            if (focusSettingsComponentMapper.has(entityId)) {
                return;
            }
            focusSettingsComponentMapper.create(entityId)
                    .focusLoseHandler(defaultHandler);
        });
    }
}
