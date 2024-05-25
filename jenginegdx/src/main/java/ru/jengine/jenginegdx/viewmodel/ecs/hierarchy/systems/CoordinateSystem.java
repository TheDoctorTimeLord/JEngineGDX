package ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.One;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector3;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components.DraggingComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components.DroppedComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.CoordinatesComponent;

@Bean
@Order(150)
@All(CoordinatesComponent.class)
@One({DraggingComponent.class, DroppedComponent.class})
public class CoordinateSystem extends IteratingSystem {
    private ComponentMapper<CoordinatesComponent> coordinatesComponentMapper;
    private ComponentMapper<DraggingComponent> draggingComponentMapper;
    private ComponentMapper<DroppedComponent> droppedComponentMapper;

    @Override
    protected void process(int id) {
        if (draggingComponentMapper.has(id)) {
            DraggingComponent dragging = draggingComponentMapper.get(id);
            CoordinatesComponent coordinates = coordinatesComponentMapper.get(id);
            Vector3 currentCoordinates = coordinates.getCoordinates();
            coordinates.coordinates(
                    currentCoordinates.x + dragging.getXDraggingOffset(),
                    currentCoordinates.y + dragging.getYDraggingOffset(),
                    Float.POSITIVE_INFINITY
            );
        }
        if (droppedComponentMapper.has(id)) {
            DroppedComponent dropped = droppedComponentMapper.get(id);
            coordinatesComponentMapper.get(id).coordinates(
                    dropped.getXDropped(),
                    dropped.getYDropped(),
                    dropped.getZActual()
            );
        }
    }
}
