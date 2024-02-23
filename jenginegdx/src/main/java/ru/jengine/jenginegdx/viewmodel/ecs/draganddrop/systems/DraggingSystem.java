package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.systems;

import com.artemis.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.beancontainer.annotations.PostConstruct;
import ru.jengine.jenginegdx.Constants.UserEvents;
import ru.jengine.jenginegdx.utils.IntBagUtils;
import ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components.DraggingComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components.DraggingSettingsSettings;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.SinglePostHandler;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.systems.EventBus;
import ru.jengine.jenginegdx.viewmodel.ecs.input.events.UserEvent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.CoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.components.MouseTouchedComponent;

@Bean
@Order(110)
public class DraggingSystem extends BaseSystem {
    private GlobalDraggingTrackingSystem draggingTracker;
    private EntitySubscription draggingSubscription;
    private DragAndDropListener listener;

    private ComponentMapper<CoordinatesComponent> coordinatesComponentMapper;
    private ComponentMapper<DraggingComponent> draggingComponentMapper;
    private ComponentMapper<MouseTouchedComponent> mouseTouchedComponentMapper;

    @Override
    protected void setWorld(World world) {
        super.setWorld(world);

        draggingSubscription = world.getAspectSubscriptionManager().get(Aspect.all(DraggingComponent.class));
    }

    @PostConstruct
    public void initialize(EventBus eventBus) {
        this.draggingTracker = world.getSystem(GlobalDraggingTrackingSystem.class);

        this.listener = new DragAndDropListener();
        world.inject(listener);

        eventBus.registerHandler(listener);
    }

    @Override
    protected void processSystem() {
        if (listener.needListenDraggingCandidates) {
            int draggableEntityId = listener.draggableEntityId;
            if (draggableEntityId != -1) {
                listener.needListenDraggingCandidates(false);
                MouseTouchedComponent mouse = mouseTouchedComponentMapper.get(draggableEntityId);
                Vector3 coordinates = coordinatesComponentMapper.get(draggableEntityId).getCoordinates();
                draggingComponentMapper.create(draggableEntityId)
                        .setOffsetToMouse(
                                mouse.getX() - coordinates.x,
                                mouse.getY() - coordinates.y)
                        .setPreviousCoordinates(coordinates);
            }
        }
        else {
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
    }

    private static class DragAndDropListener extends SinglePostHandler<UserEvent> {
        private float maxZ;
        private int draggableEntityId;
        private boolean needListenDraggingCandidates;

        private ComponentMapper<CoordinatesComponent> coordinatesComponentMapper;
        private ComponentMapper<DraggingComponent> draggingComponentMapper;
        private ComponentMapper<MouseTouchedComponent> mouseTouchedComponentMapper;
        private ComponentMapper<DraggingSettingsSettings> draggingSettingsMapper;

        public DragAndDropListener() {
            needListenDraggingCandidates(true);
        }

        public void needListenDraggingCandidates(boolean need) {
            this.needListenDraggingCandidates = need;
            if (need) {
                this.maxZ = Float.NEGATIVE_INFINITY;
                this.draggableEntityId = -1;
            }
        }

        @Override
        public void handle(UserEvent userEvent) {
            if (needListenDraggingCandidates && UserEvents.DRAG_AND_DROP.equals(userEvent.getEvent())) {
                int target = userEvent.getTargetEntityId();
                if (!coordinatesComponentMapper.has(target) || !draggingSettingsMapper.has(target)) {
                    return;
                }

                float candidateZCoordinate = coordinatesComponentMapper.get(target).getCoordinates().z;
                if (maxZ <= candidateZCoordinate) {
                    maxZ = candidateZCoordinate;
                    draggableEntityId = target;
                }
            }
            else if (UserEvents.DROP_TO.equals(userEvent.getEvent())) {
                int target = userEvent.getTargetEntityId();
                if (!draggingComponentMapper.has(target)) {
                    return;
                }

                MouseTouchedComponent mouse = mouseTouchedComponentMapper.get(target);
                DraggingComponent dragging = draggingComponentMapper.get(target);
                String draggingType = draggingSettingsMapper.get(target).getDraggableType();

                Vector3 updatedCoordinates = dragging.getPreviousCoordinate();

                if (handleDropTo(target, draggingType, mouse.getX(), mouse.getY())) {
                    updatedCoordinates.x = mouse.getX() - dragging.getXOffsetToMouse();
                    updatedCoordinates.y = mouse.getY() - dragging.getYOffsetToMouse();
                }

                coordinatesComponentMapper.get(target).coordinates(updatedCoordinates);
                draggingComponentMapper.remove(target);
                needListenDraggingCandidates(true);
            }
        }

        private boolean handleDropTo(int target, String draggingType, float x, float y) {
            return false;
        }
    }
}
