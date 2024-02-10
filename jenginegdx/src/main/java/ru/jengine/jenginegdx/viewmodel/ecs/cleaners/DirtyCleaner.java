package ru.jengine.jenginegdx.viewmodel.ecs.cleaners;

import com.artemis.ComponentMapper;
import com.artemis.annotations.One;
import com.artemis.systems.IteratingSystem;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.Constants.SystemOrder;
import ru.jengine.jenginegdx.viewmodel.ecs.bounds.BoundComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.HierarchyChildrenComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.CoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.RotationComponent;

@Bean
@Order(SystemOrder.CLEARING_SYSTEMS)
@One({CoordinatesComponent.class, RotationComponent.class, BoundComponent.class, HierarchyChildrenComponent.class})
public class DirtyCleaner extends IteratingSystem {
    //TODO автоматизировать очищение
    private ComponentMapper<CoordinatesComponent> coordinatesComponentMapper;
    private ComponentMapper<RotationComponent> rotationComponentMapper;
    private ComponentMapper<BoundComponent> boundComponentMapper;
    private ComponentMapper<HierarchyChildrenComponent> hierarchyChildrenComponentMapper;

    @Override
    protected void process(int entityId) {
        if (coordinatesComponentMapper.has(entityId)) {
            coordinatesComponentMapper.get(entityId).clear();
        }
        if (rotationComponentMapper.has(entityId)) {
            rotationComponentMapper.get(entityId).clear();
        }
        if (boundComponentMapper.has(entityId)) {
            boundComponentMapper.get(entityId).clear();
        }
        if (hierarchyChildrenComponentMapper.has(entityId)) {
            hierarchyChildrenComponentMapper.get(entityId).clear();
        }
    }
}
