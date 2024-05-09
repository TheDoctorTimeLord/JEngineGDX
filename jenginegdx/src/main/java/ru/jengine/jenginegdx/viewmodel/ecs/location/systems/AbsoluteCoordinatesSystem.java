package ru.jengine.jenginegdx.viewmodel.ecs.location.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.HierarchyComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.CoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.components.AbsoluteCoordinatesComponent;

@Bean
@Order(6)
@All({CoordinatesComponent.class})
public class AbsoluteCoordinatesSystem extends IteratingSystem {
    private ComponentMapper<CoordinatesComponent> coordinatesMapper;
    private ComponentMapper<AbsoluteCoordinatesComponent> absoluteCoordinatesMapper;
    private ComponentMapper<HierarchyComponent> hierarchyMapper;

    @Override
    protected void process(int entity) {
        CoordinatesComponent coordinates = coordinatesMapper.get(entity);

        if (!coordinates.isDirty()) return;
        int dirty = findHighestEntityWithChangedCoordinates(entity);
        if (dirty != entity) return;

        AbsoluteCoordinatesComponent absoluteCoordinates = absoluteCoordinatesMapper.create(dirty)
                .coordinates(coordinates.x(), coordinates.y(), coordinates.z());
        alignTree(dirty, absoluteCoordinates);
    }

    private void alignTree(int parent, AbsoluteCoordinatesComponent parentCoordinates) {
        if (!hierarchyMapper.has(parent)) return;

        HierarchyComponent hierarchy = hierarchyMapper.get(parent);
        int[] data = hierarchy.childrenId.getData();
        for (int i = 0; i < hierarchy.childrenId.size(); i++) {
            int child = data[i];
            if (!coordinatesMapper.has(child)) continue;
            CoordinatesComponent childCoordinates = coordinatesMapper.get(child);
            AbsoluteCoordinatesComponent childAbsoluteCoordinates = absoluteCoordinatesMapper.create(child).coordinates(
                    parentCoordinates.x() + childCoordinates.x(),
                    parentCoordinates.y() + childCoordinates.y(),
                    parentCoordinates.z() + childCoordinates.z());
            alignTree(child, childAbsoluteCoordinates);
        }
    }

    private int findHighestEntityWithChangedCoordinates(int entity){
        int current = entity;

        do {
            current = hierarchyMapper.has(current) ? hierarchyMapper.get(current).parentId : -1;
            if (current == -1) return entity;
        } while (!coordinatesMapper.get(current).isDirty());

        return current;
    }

}
