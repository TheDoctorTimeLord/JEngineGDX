package ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.systems;

import com.artemis.ComponentMapper;
import com.artemis.World;
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
@Order(2)
@All(MouseEventComponent.class)
public class GlobalDraggingTrackingSystem extends IteratingSystem {
    private ComponentMapper<MouseEventComponent> mouseEventComponentMapper;
    private ComponentMapper<DraggingTrackingComponent> draggingTrackingComponentMapper;
    private int draggingTracker;

    @Override
    protected void setWorld(World world) {
        super.setWorld(world);
        this.draggingTracker = world.create();
    }

    @Override
    protected void process(int entityId) {
        MouseEventComponent mouse = mouseEventComponentMapper.get(entityId);
        if (MouseEventType.DRAGGING.equals(mouse.getEventType())) {
            DraggingTrackingComponent draggingCoordinate = draggingTrackingComponentMapper.has(draggingTracker)
                    ? draggingTrackingComponentMapper.get(draggingTracker)
                    : draggingTrackingComponentMapper.create(draggingTracker);
            draggingCoordinate.draggingPosition(mouse.getMouseX(), mouse.getMouseY());
        }
        else {
            draggingTrackingComponentMapper.remove(draggingTracker);
        }
    }

    @Nullable
    public Vector2 getDraggingCoordinates() {
        if (!draggingTrackingComponentMapper.has(draggingTracker)) {
            return null;
        }

        DraggingTrackingComponent mouse = draggingTrackingComponentMapper.get(draggingTracker);
        return new Vector2(mouse.getDraggingX(), mouse.getDraggingY());
    }
}
