package ru.jengine.jenginegdx.viewmodel.ecs.label;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector3;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.Constants.SystemOrder;
import ru.jengine.jenginegdx.viewmodel.ecs.location.CoordinatesComponent;

@Bean
@Order(SystemOrder.CALCULATION_SYSTEMS + 200)
@All({CoordinatesComponent.class, LabelComponent.class})
public class LabelRecalculationSystem extends IteratingSystem {
    private ComponentMapper<CoordinatesComponent> coordinatesComponentMapper;
    private ComponentMapper<LabelComponent> labelComponentMapper;

    @Override
    protected void process(int entityId) {
        CoordinatesComponent coordinatesComponent = coordinatesComponentMapper.get(entityId);
        if (coordinatesComponent.isDirty()) {
            Vector3 coordinates = coordinatesComponent.coordinates;
            labelComponentMapper.get(entityId).position(coordinates.x, coordinates.y);
        }
    }
}
