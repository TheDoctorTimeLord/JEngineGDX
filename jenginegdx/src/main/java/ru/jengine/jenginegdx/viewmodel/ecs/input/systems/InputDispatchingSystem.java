package ru.jengine.jenginegdx.viewmodel.ecs.input.systems;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.annotations.All;
import com.artemis.utils.IntBag;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.ClassesExtends;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.utils.IntBagUtils;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.sequence.SequentialEventHandler;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.systems.EventBus;
import ru.jengine.jenginegdx.viewmodel.ecs.focus.components.FocusComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.input.components.InputComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.input.components.InputEventHandlingComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.input.events.InputMappingEvent;
import ru.jengine.jenginegdx.viewmodel.ecs.utils.MarkerComponentsSystem;

import java.util.List;

@Bean
@Order(300)
public class InputDispatchingSystem extends MarkerComponentsSystem {
    private final EventBus eventBus;

    private ComponentMapper<InputEventHandlingComponent> inputEventHandlingComponentMapper;

    @All({FocusComponent.class, InputEventHandlingComponent.class})
    private EntitySubscription focusedEntitySubscription;
    private IntBag focusedEntities;

    public InputDispatchingSystem(
            @ClassesExtends(InputComponent.class) List<Class<? extends Component>> inputComponents,
            EventBus eventBus)
    {
        super(inputComponents);
        this.eventBus = eventBus;
        this.eventBus.registerHandler(new SequentialEventHandler<>(InputMappingEvent.class));
    }

    @Override
    protected boolean checkProcessing() {
        focusedEntities = focusedEntitySubscription.getEntities();
        return !focusedEntities.isEmpty();
    }

    @Override
    protected void processEntity(int entityId, ComponentMapper<? extends Component> mapper) {
        InputComponent input = (InputComponent) mapper.get(entityId);
        String inputCode = input.toEventCode();

        IntBagUtils.forEach(focusedEntities, focusedId -> {
            InputEventHandlingComponent inputEventHandling = inputEventHandlingComponentMapper.get(focusedId);
            String[] handling = inputEventHandling.getHandling(inputCode);
            if (handling != null) {
                eventBus.registerEvent(new InputMappingEvent(focusedId, handling));
            }
        });

        mapper.remove(entityId);
    }
}
