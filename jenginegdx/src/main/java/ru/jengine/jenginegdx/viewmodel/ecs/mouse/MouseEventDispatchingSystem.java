package ru.jengine.jenginegdx.viewmodel.ecs.mouse;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.World;
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
import ru.jengine.jenginegdx.viewmodel.ecs.camera.GameCamera;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.EventBus;
import ru.jengine.jenginegdx.viewmodel.ecs.input.UserEvent;
import ru.jengine.jenginegdx.viewmodel.ecs.input.UserEventHandlingComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.input.systems.InputProcessingSystem;
import ru.jengine.jenginegdx.viewmodel.ecs.location.CoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.RotationComponent;

import java.util.List;

@Bean
@Order(2)
@All(MouseEventComponent.class)
public class MouseEventDispatchingSystem extends IteratingSystem {
    private final EventBus eventBus;
    private final GameCamera camera;
    private ComponentMapper<CoordinatesComponent> coordinatesComponentMapper;
    private ComponentMapper<RotationComponent> rotationComponentMapper;
    private ComponentMapper<MouseTouchBoundComponent> mouseTouchBoundComponentMapper;
    private ComponentMapper<MouseEventComponent> mouseEventComponentMapper;
    private ComponentMapper<UserEventHandlingComponent> userEventHandlingComponentMapper;
    private ComponentMapper<MouseTouchedComponent> mouseTouchedComponentMapper;

    private EntitySubscription boundSubscription;
    private List<BoundedEntity> boundedEntities;

    public MouseEventDispatchingSystem(EventBus eventBus, GameCamera camera) {
        this.eventBus = eventBus;
        this.camera = camera;
    }

    @Override
    protected void setWorld(World world) {
        super.setWorld(world);
        boundSubscription = world.getAspectSubscriptionManager().get(
                Aspect.all(CoordinatesComponent.class, MouseTouchBoundComponent.class, UserEventHandlingComponent.class) //TODO переделать для иерархии объектов
        );
    }

    @PostConstruct
    private void setMouseTrigger() {
        world.getSystem(InputProcessingSystem.class).registerTrigger(new MouseInputTrigger(camera));
    }

    @Override
    protected void begin() {
        Pool<BoundedEntity> pool = Pools.get(BoundedEntity.class);
        boundedEntities = IntBagUtils.map(boundSubscription.getEntities(), id -> {
                    BoundedEntity entity = pool.obtain();
                    entity.id = id;
                    entity.coordinates = coordinatesComponentMapper.get(id).getCoordinates();
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

        for (BoundedEntity entity : boundedEntities) {
            if (entity.inBound(mouseX, mouseY)) {
                String handling = entity.userEventHandling.getHandling(eventTypeCode);
                if (handling != null) {
                    mouseTouchedComponentMapper.create(entity.id).setTouched(mouseX, mouseY);
                    eventBus.registerEvent(new UserEvent(entity.id, handling));
                }
            }
        }
    }

    @Override
    protected void end() {
        Pool<BoundedEntity> pool = Pools.get(BoundedEntity.class);
        for (BoundedEntity entity : boundedEntities) {
            pool.free(entity);
        }
    }

    private static class BoundedEntity implements Poolable {
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
