package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.beancontainer.annotations.PostConstruct;
import ru.jengine.jenginegdx.Constants.UserEvents;
import ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components.DraggingComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.events.DroppedTo;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.SinglePostHandler;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.systems.EventBus;
import ru.jengine.jenginegdx.viewmodel.ecs.input.events.UserEvent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.CoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.components.MouseTouchedComponent;

@Bean
@Order(110)
@All({DraggingComponent.class, CoordinatesComponent.class})
public class DraggingSystem extends IteratingSystem {
    private GlobalDraggingTrackingSystem draggingTracker;
    private ComponentMapper<CoordinatesComponent> coordinatesComponentMapper;
    private ComponentMapper<DraggingComponent> draggingComponentMapper;
    private ComponentMapper<MouseTouchedComponent> mouseTouchedComponentMapper;

    @PostConstruct
    public void externalDependencies() {
        this.draggingTracker = world.getSystem(GlobalDraggingTrackingSystem.class);
    }

    @PostConstruct
    public void setListeners(EventBus eventBus) {
        eventBus.registerHandler(new SinglePostHandler<UserEvent>() {
            @Override
            public void handle(UserEvent userEvent) {
                if (UserEvents.DRAG_AND_DROP.equals(userEvent.getEvent())) {
                    int target = userEvent.getTargetEntityId();
                    if (draggingComponentMapper.has(target)) {
                        return;
                    }

                    Vector3 coordinates = coordinatesComponentMapper.get(target).getCoordinates();
                    MouseTouchedComponent mouse = mouseTouchedComponentMapper.get(target);

                    draggingComponentMapper.create(target).setOffsetToMouse(
                            mouse.getX() - coordinates.x,
                            mouse.getY() - coordinates.y
                    );
                }
                if (UserEvents.DROP_TO.equals(userEvent.getEvent())) {
                    int target = userEvent.getTargetEntityId();
                    if (!draggingComponentMapper.has(target)) {
                        return;
                    }

                    MouseTouchedComponent mouse = mouseTouchedComponentMapper.get(target);
                    draggingComponentMapper.remove(target);
                    eventBus.registerEvent(new DroppedTo(target, mouse.getX(), mouse.getY()));
                }
            }
        });
    }

    @Override
    protected void process(int entityId) {
        if (draggingTracker == null) {
            return;
        }

        Vector2 draggingCoordinates = draggingTracker.getDraggingCoordinates();
        if (draggingCoordinates == null) {
            return;
        }

        DraggingComponent dragging = draggingComponentMapper.get(entityId);
        CoordinatesComponent coordinatesComponent = coordinatesComponentMapper.get(entityId);
        coordinatesComponent.coordinates(
                draggingCoordinates.x - dragging.getXOffsetToMouse(),
                draggingCoordinates.y - dragging.getYOffsetToMouse(),
                coordinatesComponent.getCoordinates().z
        );
    }
}
