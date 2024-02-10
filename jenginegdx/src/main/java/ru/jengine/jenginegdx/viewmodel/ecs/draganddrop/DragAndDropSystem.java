package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.One;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.Constants.SystemOrder;
import ru.jengine.jenginegdx.Constants.UserEvents;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.EventBus;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.SinglePostHandler;
import ru.jengine.jenginegdx.viewmodel.ecs.location.CoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.MouseUserEvent;

@Bean
@Order(SystemOrder.VIEW_MODEL_SYSTEMS + 1)
@One({DraggingComponent.class, DragAndDropInitialComponent.class})
public class DragAndDropSystem extends BaseEntitySystem {
    private ComponentMapper<DraggingComponent> draggingComponentMapper;
    private ComponentMapper<DragAndDropInitialComponent> dragAndDropInitialComponentMapper;
    private ComponentMapper<CoordinatesComponent> coordinatesComponentMapper;

    public DragAndDropSystem(EventBus eventBus) {
        eventBus.registerHandler(new SinglePostHandler<MouseUserEvent>() {
            @Override
            public void handle(MouseUserEvent event) {
                if (UserEvents.DRAG_AND_DROP.equals(event.getUserEvent())) {
                    if (draggingComponentMapper.has(event.getTargetEntityId())) {
                        System.out.println("Handle dragged: " + event.getTargetEntityId());
                    }
                }
            }
        });
    }

    @Override
    protected void processSystem() {

    }
}
