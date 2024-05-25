package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components.DraggingTrackingComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.MouseEventType;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.components.MouseEventComponent;

import javax.annotation.Nullable;

@Bean
@Order(250)
@All(MouseEventComponent.class)
public class GlobalDraggingTrackingSystem extends IteratingSystem {
    private ComponentMapper<MouseEventComponent> mouseEventComponentMapper;
    private ComponentMapper<DraggingTrackingComponent> draggingTrackingComponentMapper;
    private int draggingTracker;
    private float lastX = 0;
    private float lastY = 0;
    private boolean receivedEvent = false;

    @Override
    protected void initialize() {
        this.draggingTracker = world.create();
    }

    @Override
    protected void process(int entityId) {
        receivedEvent = true;
        MouseEventComponent mouse = mouseEventComponentMapper.get(entityId);
        if (MouseEventType.DRAGGING.equals(mouse.getEventType())) {
            DraggingTrackingComponent draggingCoordinate = draggingTrackingComponentMapper.has(draggingTracker)
                    ? draggingTrackingComponentMapper.get(draggingTracker)
                    : draggingTrackingComponentMapper.create(draggingTracker);
            draggingCoordinate.draggingOffset(mouse.getX() - lastX, mouse.getY() - lastY);
        }
        lastX = mouse.getX();
        lastY = mouse.getY();
    }

    @Override
    protected void end() {
        if (!receivedEvent && draggingTrackingComponentMapper.has(draggingTracker)) {
            draggingTrackingComponentMapper.remove(draggingTracker);
        }
        receivedEvent = false;
    }

    @Nullable
    public Vector2 getDraggingOffset() {
        if (!draggingTrackingComponentMapper.has(draggingTracker)) {
            return null;
        }

        DraggingTrackingComponent mouse = draggingTrackingComponentMapper.get(draggingTracker);
        return new Vector2(mouse.getOffsetX(), mouse.getOffsetY());
    }
}
