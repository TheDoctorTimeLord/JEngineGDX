package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.systems;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.annotations.All;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.beancontainer.annotations.PostConstruct;
import ru.jengine.jenginegdx.Constants.UserEvents;
import ru.jengine.jenginegdx.utils.IntBagUtils;
import ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components.DraggingComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components.DraggingSettingsComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components.DroppedContainerComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.SinglePostHandler;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.systems.EventBus;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.CoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.input.events.UserEvent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.RotationComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.components.MouseTouchBoundComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.components.MouseTouchedComponent;

@Bean
@Order(110)
public class DraggingSystem extends BaseSystem { //TODO разобраться с относительным/абсолютными координатами и иерархией
    private GlobalDraggingTrackingSystem draggingTracker;
    @All({CoordinatesComponent.class, DraggingComponent.class})
    private EntitySubscription draggingSubscription;
    @All({CoordinatesComponent.class, MouseTouchBoundComponent.class, DroppedContainerComponent.class})
    private EntitySubscription containerDroppedSubscription;
    private DragAndDropListener listener;
    private ComponentMapper<CoordinatesComponent> coordinatesComponentMapper;
    private ComponentMapper<DraggingComponent> draggingComponentMapper;
    private ComponentMapper<DraggingSettingsComponent> draggingSettingsComponentMapper;
    private ComponentMapper<MouseTouchedComponent> mouseTouchedComponentMapper;
    private ComponentMapper<MouseTouchBoundComponent> mouseTouchBoundComponentMapper;
    private ComponentMapper<DroppedContainerComponent> containerDroppedComponentMapper;
    private ComponentMapper<RotationComponent> rotationComponentMapper;

    @PostConstruct
    public void initialize(EventBus eventBus) {
        this.draggingTracker = world.getSystem(GlobalDraggingTrackingSystem.class);

        listener = new DragAndDropListener(coordinatesComponentMapper, draggingComponentMapper,
                draggingSettingsComponentMapper);
        eventBus.registerHandler(listener);
    }

    @Override
    protected void processSystem() {
        switch (listener.listenerMode) {
            case LISTEN_CANDIDATES -> {
                int draggableEntityId = listener.draggableEntityId;
                if (draggableEntityId != -1) {
                    listener.setListenerMode(ListenerMode.WAITED);
                    MouseTouchedComponent mouse = mouseTouchedComponentMapper.get(draggableEntityId);
                    CoordinatesComponent coordinatesComponent = coordinatesComponentMapper.get(draggableEntityId);
                    Vector3 coordinates = coordinatesComponent.getCoordinates();
                    draggingComponentMapper.create(draggableEntityId)
                            .setOffsetToMouse(
                                    mouse.getX() - coordinates.x,
                                    mouse.getY() - coordinates.y)
                            .setPreviousCoordinates(coordinates);
                    coordinatesComponent.coordinates(coordinates.x, coordinates.y, Float.POSITIVE_INFINITY);
                }
            }
            case WAITED -> {
                Vector2 draggingCoordinates = draggingTracker.getDraggingCoordinates();
                if (draggingCoordinates == null) {
                    return;
                }

                IntBagUtils.forEach(draggingSubscription.getEntities(), id -> {
                    DraggingComponent draggingComponent = draggingComponentMapper.get(id);
                    coordinatesComponentMapper.get(id).coordinates(
                            draggingCoordinates.x - draggingComponent.getXOffsetToMouse(),
                            draggingCoordinates.y - draggingComponent.getYOffsetToMouse(),
                            Float.POSITIVE_INFINITY
                    );
                });
            }
            case HOLD_DROPPED_CANDIDATE -> {
                int draggableEntityId = listener.draggableEntityId;
                String draggingType = draggingSettingsComponentMapper.get(draggableEntityId).getDraggableType();
                MouseTouchedComponent mouse = mouseTouchedComponentMapper.get(draggableEntityId);
                DraggingComponent dragging = draggingComponentMapper.get(draggableEntityId);

                boolean wasMatched = false;
                IntBag entities = containerDroppedSubscription.getEntities();
                int[] ids = entities.getData();
                for (int i = 0; i < entities.size(); i++) {
                    int containerId = ids[i];

                    Vector3 coordinates = coordinatesComponentMapper.get(containerId).getCoordinates();
                    DroppedContainerComponent containerDropped = containerDroppedComponentMapper.get(containerId);
                    MouseTouchBoundComponent mouseTouchBound = mouseTouchBoundComponentMapper.get(containerId);
                    float rotation = rotationComponentMapper.has(containerId)
                            ? rotationComponentMapper.get(containerId).getRotation()
                            : RotationComponent.DEFAULT_ROTATION;

                    if (mouseTouchBound.inBound(mouse.getX(), mouse.getY(), coordinates.x, coordinates.y, rotation)
                            && containerDropped.getTargetDraggingType().equals(draggingType))
                    {
                        containerDropped.getDroppedHandler().handle(
                                draggableEntityId,
                                containerId,
                                mouse.getX(), mouse.getY(),
                                dragging.getXOffsetToMouse(), dragging.getYOffsetToMouse(),
                                draggingType);
                        wasMatched = true;
                        break;
                    }
                }

                CoordinatesComponent coordinates = coordinatesComponentMapper.get(draggableEntityId);
                Vector3 oldCoords = dragging.getPreviousCoordinate();
                Vector3 newCoords = coordinates.getCoordinates();
                coordinates.coordinates(
                        wasMatched ? newCoords.x : oldCoords.x,
                        wasMatched ? newCoords.y : oldCoords.y,
                        oldCoords.z
                );

                draggingComponentMapper.remove(draggableEntityId);
                listener.setListenerMode(ListenerMode.LISTEN_CANDIDATES);
            }
        }
    }

    private enum ListenerMode {
        LISTEN_CANDIDATES, HOLD_DROPPED_CANDIDATE, WAITED
    }

    private static class DragAndDropListener extends SinglePostHandler<UserEvent> {
        private final ComponentMapper<CoordinatesComponent> coordinatesComponentMapper;
        private final ComponentMapper<DraggingComponent> draggingComponentMapper;
        private final ComponentMapper<DraggingSettingsComponent> draggingSettingsMapper;
        private ListenerMode listenerMode;
        private int draggableEntityId;
        private float maxZ;

        public DragAndDropListener(ComponentMapper<CoordinatesComponent> coordinatesComponentMapper,
                                   ComponentMapper<DraggingComponent> draggingComponentMapper,
                                   ComponentMapper<DraggingSettingsComponent> draggingSettingsMapper)
        {
            this.coordinatesComponentMapper = coordinatesComponentMapper;
            this.draggingComponentMapper = draggingComponentMapper;
            this.draggingSettingsMapper = draggingSettingsMapper;
            setListenerMode(ListenerMode.LISTEN_CANDIDATES);
        }

        public void setListenerMode(ListenerMode listenerMode) {
            this.listenerMode = listenerMode;
            if (ListenerMode.LISTEN_CANDIDATES.equals(listenerMode)) {
                this.maxZ = Float.NEGATIVE_INFINITY;
                this.draggableEntityId = -1;
            }
        }

        @Override
        public void handle(UserEvent userEvent) {
            if (ListenerMode.LISTEN_CANDIDATES.equals(listenerMode) && UserEvents.DRAG_AND_DROP.equals(userEvent.getEvent())) {
                int target = userEvent.getTargetEntityId();
                if (!coordinatesComponentMapper.has(target) || !draggingSettingsMapper.has(target))
                {
                    return;
                }

                float candidateZCoordinate = coordinatesComponentMapper.get(target).getCoordinates().z;
                if (maxZ <= candidateZCoordinate) {
                    maxZ = candidateZCoordinate;
                    draggableEntityId = target;
                }
            }
            if (ListenerMode.WAITED.equals(listenerMode) && UserEvents.DROP_TO.equals(userEvent.getEvent())) {
                if (!UserEvents.DROP_TO.equals(userEvent.getEvent())) {
                    return;
                }

                int target = userEvent.getTargetEntityId();
                if (!draggingComponentMapper.has(target)) {
                    return;
                }

                setListenerMode(ListenerMode.HOLD_DROPPED_CANDIDATE);
                draggableEntityId = target;
            }
        }
    }
}
