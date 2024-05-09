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
import ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components.DroppedComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components.DroppedContainerComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.SinglePostHandler;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.systems.EventBus;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.CoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.input.events.UserEvent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.components.RotationComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.components.MouseTouchBoundComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.components.MouseTouchedComponent;

@Bean
@Order(110)
public class DraggingSystem extends BaseSystem {
    private GlobalDraggingTrackingSystem draggingTracker;
    @All({CoordinatesComponent.class, DraggingComponent.class})
    private EntitySubscription draggingSubscription;
    @All({CoordinatesComponent.class, MouseTouchBoundComponent.class, DroppedContainerComponent.class})
    private EntitySubscription containerDroppedSubscription;
    @All({DroppedComponent.class})
    private EntitySubscription droppedSubscription;
    private DragAndDropListener listener;
    private ComponentMapper<CoordinatesComponent> coordinatesComponentMapper;
    private ComponentMapper<DraggingComponent> draggingComponentMapper;
    private ComponentMapper<DraggingSettingsComponent> draggingSettingsComponentMapper;
    private ComponentMapper<MouseTouchedComponent> mouseTouchedComponentMapper;
    private ComponentMapper<MouseTouchBoundComponent> mouseTouchBoundComponentMapper;
    private ComponentMapper<DroppedContainerComponent> containerDroppedComponentMapper;
    private ComponentMapper<RotationComponent> rotationComponentMapper;
    private ComponentMapper<DroppedComponent> droppedComponentMapper;

    @PostConstruct
    public void initialize(EventBus eventBus) {
        this.draggingTracker = world.getSystem(GlobalDraggingTrackingSystem.class);

        listener = new DragAndDropListener(coordinatesComponentMapper, draggingComponentMapper,
                draggingSettingsComponentMapper);
        eventBus.registerHandler(listener);
    }

    @Override
    protected void processSystem() {
        IntBagUtils.forEach(droppedSubscription.getEntities(), id -> droppedComponentMapper.remove(id));

        switch (listener.listenerMode) {
            case LISTEN_CANDIDATES -> startDraggingEntity();
            case WAITED_DROP -> moveDraggedEntity();
            case HOLD_DROPPED_CANDIDATE -> dropEntityToHoldableContainer();
        }
    }

    private void startDraggingEntity() {
        int draggableEntityId = listener.draggableEntityId;
        if (draggableEntityId != -1) {
            listener.setListenerMode(ListenerMode.WAITED_DROP);
            MouseTouchedComponent mouse = mouseTouchedComponentMapper.get(draggableEntityId);
            Vector3 coordinates = coordinatesComponentMapper.get(draggableEntityId).getCoordinates();
            draggingComponentMapper.create(draggableEntityId)
                    .draggingOffset(Vector2.Zero)
                    .offsetToMouse(
                            mouse.getX() - coordinates.x,
                            mouse.getY() - coordinates.y)
                    .previousCoordinates(coordinates);
        }
    }

    private void moveDraggedEntity() {
        Vector2 draggingOffset = draggingTracker.getDraggingOffset();

        IntBagUtils.forEach(draggingSubscription.getEntities(), id -> draggingComponentMapper.get(id)
                .draggingOffset(draggingOffset == null ? Vector2.Zero : draggingOffset));
    }

    private void dropEntityToHoldableContainer() {
        int draggableEntityId = listener.draggableEntityId;
        DraggingComponent dragging = draggingComponentMapper.get(draggableEntityId);

        boolean hasContainer = hasContainerToDropping(draggableEntityId, dragging);

        Vector3 oldCoords = dragging.getPreviousCoordinate();
        Vector3 newCoords = coordinatesComponentMapper.get(draggableEntityId).getCoordinates();

        droppedComponentMapper.create(draggableEntityId).droppedTo(
                hasContainer ? newCoords.x : oldCoords.x,
                hasContainer ? newCoords.y : oldCoords.y,
                oldCoords.z
        );

        draggingComponentMapper.remove(draggableEntityId);
        listener.setListenerMode(ListenerMode.LISTEN_CANDIDATES);
    }

    private boolean hasContainerToDropping(int draggableEntityId, DraggingComponent dragging) {
        String draggingType = draggingSettingsComponentMapper.get(draggableEntityId).getDraggableType();
        MouseTouchedComponent mouse = mouseTouchedComponentMapper.get(draggableEntityId);

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
                return true;
            }
        }
        return false;
    }

    private enum ListenerMode {
        LISTEN_CANDIDATES, HOLD_DROPPED_CANDIDATE, WAITED_DROP
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
            if (ListenerMode.WAITED_DROP.equals(listenerMode) && UserEvents.DROP_TO.equals(userEvent.getEvent())) {
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
