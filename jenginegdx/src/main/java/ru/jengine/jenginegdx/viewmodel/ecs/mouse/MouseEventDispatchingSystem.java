package ru.jengine.jenginegdx.viewmodel.ecs.mouse;

import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import ru.jengine.beancontainer.annotations.Order;

//@Bean
@Order(2)
@All(MouseEventComponent.class)
public class MouseEventDispatchingSystem extends IteratingSystem {
    @Override
    protected void process(int entityId) {

    }
    /*private final EventBus eventBus;
    private ComponentMapper<MouseEventComponent> mouseEventComponentMapper;
    private ComponentMapper<UserEventHandlingComponent> userEventHandlingComponentMapper;

    private EntitySubscription boundSubscription;
    private BoundEntity[] boundedEntities;

    public MouseEventDispatchingSystem(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    protected void setWorld(World world) {
        super.setWorld(world);
        boundSubscription = world.getAspectSubscriptionManager().get(Aspect.all(GlobalBoundComponent.class));
    }

    @Override
    protected void begin() {
        Pool<BoundEntity> boundEntityPool = Pools.get(BoundEntity.class);
        boundedEntities = IntBagUtils.map(boundSubscription.getEntities(), id ->
                        boundEntityPool.obtain()
                                .id(id)
                                .bound(globalBoundComponentMapper.get(id).bound))
                .toArray(BoundEntity[]::new);
    }

    @Override
    protected void process(int entityId) {
        MouseEventComponent mouseEventComponent = mouseEventComponentMapper.get(entityId);
        Vector2 mouseCoordinates = new Vector2(mouseEventComponent.mouseX, mouseEventComponent.mouseY);
        String eventTypeCode = mouseEventComponent.eventType.getUserEventCode();
        for (BoundEntity boundedEntity : boundedEntities) {
            if (boundedEntity.bound.inBound(mouseCoordinates) && userEventHandlingComponentMapper.has(boundedEntity.id)) {
                int id = boundedEntity.id;
                String dispatchedEvent = dispatchHierarchy(id, eventTypeCode);
                if (dispatchedEvent != null) {
                    eventBus.registerEvent(new MouseUserEvent(id, dispatchedEvent, mouseEventComponent.mouseX, mouseEventComponent.mouseY));
                }
            }
        }
    }

    @Override
    protected void end() {
        Pool<BoundEntity> boundEntityPool = Pools.get(BoundEntity.class);
        for (BoundEntity bound : boundedEntities) {
            boundEntityPool.free(bound);
        }
    }

    @Nullable
    private String dispatchHierarchy(int entityId, String event) {
        if (userEventHandlingComponentMapper.has(entityId)) {
            String handling = userEventHandlingComponentMapper.get(entityId).getHandling(event);
            if (handling != null) {
                return handling;
            }
        }

        if (hierarchyChildrenComponentMapper.has(entityId)) {
            IntBag children = hierarchyChildrenComponentMapper.get(entityId).children;
            int[] ids = children.getData();
            for (int i = 0, size = children.size(); i < size; i++) {
                String handling = dispatchHierarchy(ids[i], event);
                if (handling != null) {
                    return handling;
                }
            }
        }

        return null;
    }

    private static class BoundEntity implements Poolable {
        private int id;
        private PositionedFigure bound;

        public BoundEntity id(int id) {
            this.id = id;
            return this;
        }

        public BoundEntity bound(PositionedFigure bound) {
            this.bound = bound;
            return this;
        }

        @Override
        public void reset() {
            this.id = -1;
            this.bound = null;
        }
    }*/
}
