package ru.jengine.jenginegdx.viewmodel.ecs.mouse.systems;

import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.Pools;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.beancontainer.annotations.PostConstruct;
import ru.jengine.jenginegdx.utils.IntBagUtils;
import ru.jengine.jenginegdx.viewmodel.camera.GameCamera;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.systems.EventBus;
import ru.jengine.jenginegdx.viewmodel.ecs.input.components.UserEventHandlingComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.input.events.UserEvent;
import ru.jengine.jenginegdx.viewmodel.ecs.input.systems.InputProcessingSystem;
import ru.jengine.jenginegdx.viewmodel.ecs.location.AbsoluteCoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.RotationComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.MouseInputTrigger;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.components.MouseEventComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.components.MouseTouchBoundComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.components.MouseTouchedComponent;

import java.util.List;

@Bean
@Order(3)
@All(MouseEventComponent.class)
public class MouseEventDispatchingSystem extends IteratingSystem {
    private final EventBus eventBus;
    private final GameCamera camera;
    private ComponentMapper<AbsoluteCoordinatesComponent> absoluteCoordinatesComponentMapper;
    private ComponentMapper<RotationComponent> rotationComponentMapper;
    private ComponentMapper<MouseTouchBoundComponent> mouseTouchBoundComponentMapper;
    private ComponentMapper<MouseEventComponent> mouseEventComponentMapper;
    private ComponentMapper<UserEventHandlingComponent> userEventHandlingComponentMapper;
    private ComponentMapper<MouseTouchedComponent> mouseTouchedComponentMapper;

    @All({AbsoluteCoordinatesComponent.class, MouseTouchBoundComponent.class, UserEventHandlingComponent.class})
    private EntitySubscription clickableElementsSubscription;
    private List<ClickableBoundedEntity> clickableBoundedEntities;

    public MouseEventDispatchingSystem(EventBus eventBus, GameCamera camera) {
        this.eventBus = eventBus;
        this.camera = camera;
    }

    @PostConstruct
    private void setMouseTrigger() {
        world.getSystem(InputProcessingSystem.class).registerTrigger(new MouseInputTrigger(camera));
    }

    @Override
    protected void begin() {
        Pool<ClickableBoundedEntity> pool = Pools.get(ClickableBoundedEntity.class);
        clickableBoundedEntities = IntBagUtils.map(clickableElementsSubscription.getEntities(), id -> {
                    ClickableBoundedEntity entity = pool.obtain();
                    entity.id = id;
                    entity.coordinates = absoluteCoordinatesComponentMapper.get(id).getCoordinates();
                    entity.rotation = rotationComponentMapper.has(id)
                            ? rotationComponentMapper.get(id).getRotation()
                            : RotationComponent.DEFAULT_ROTATION;
                    entity.boundComponent = mouseTouchBoundComponentMapper.get(id);
                    entity.userEventHandling = userEventHandlingComponentMapper.get(id);
                    return entity;
                })
                .toList();
    }

    @Override
    protected void process(int entityId) {
        MouseEventComponent mouseEventComponent = mouseEventComponentMapper.get(entityId);
        float mouseX = mouseEventComponent.getMouseX();
        float mouseY = mouseEventComponent.getMouseY();
        String eventTypeCode = mouseEventComponent.getEventType().getUserEventCode();

        for (ClickableBoundedEntity entity : clickableBoundedEntities) {
            if (entity.inBound(mouseX, mouseY)) {
                mouseTouchedComponentMapper.create(entity.id).setTouched(mouseX, mouseY);
                String handling = entity.userEventHandling.getHandling(eventTypeCode);
                if (handling != null) {
                    eventBus.registerEvent(new UserEvent(entity.id, handling));
                }
            }
        }
        world.delete(entityId);
    }

    @Override
    protected void end() {
        Pool<ClickableBoundedEntity> pool = Pools.get(ClickableBoundedEntity.class);
        for (ClickableBoundedEntity entity : clickableBoundedEntities) {
            pool.free(entity);
        }
    }

    private static class ClickableBoundedEntity implements Poolable {
        private int id;
        private Vector3 coordinates;
        private float rotation;
        private MouseTouchBoundComponent boundComponent;
        private UserEventHandlingComponent userEventHandling;

        public boolean inBound(float x, float y) {
            return boundComponent.inBound(x, y, coordinates.x, coordinates.y, rotation);
        }

        @Override
        public void reset() {
            this.id = -1;
            this.coordinates = Vector3.Zero;
            this.rotation = RotationComponent.DEFAULT_ROTATION;
            this.boundComponent = null;
            this.userEventHandling = null;
        }
    }
}
