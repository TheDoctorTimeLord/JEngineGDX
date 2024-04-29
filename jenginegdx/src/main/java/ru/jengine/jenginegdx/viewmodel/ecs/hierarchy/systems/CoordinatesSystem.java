package ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.HierarchyComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.CoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.AbsoluteCoordinatesComponent;

@Bean
@All({CoordinatesComponent.class, HierarchyComponent.class})
public class CoordinatesSystem extends IteratingSystem {
    protected ComponentMapper<CoordinatesComponent> coordinatesMapper;
    protected ComponentMapper<AbsoluteCoordinatesComponent> absoluteCoordinatesMapper;
    protected ComponentMapper<HierarchyComponent> hierarchyMapper;

    @Override
    protected void process(int entity) {
        CoordinatesComponent coordinates = coordinatesMapper.get(entity);

        if (!coordinates.isDirty()) return;
        int dirty = findHighestDirtyParent(entity);
        if (dirty != entity) return;
        int dirtyParent = hierarchyMapper.get(dirty).parentId;

        if (dirtyParent == -1) {
            AbsoluteCoordinatesComponent absoluteCoordinates = absoluteCoordinatesMapper.create(dirty);
            absoluteCoordinates.coordinates(coordinates.x(), coordinates.y(), coordinates.z());
            alignTree(dirty);
        } else {
            alignTree(dirtyParent);
        }
    }

    void alignTree(int root) {
        coordinatesMapper.get(root).clear();
        HierarchyComponent hierarchy = hierarchyMapper.get(root);
        AbsoluteCoordinatesComponent absoluteCoordinates = absoluteCoordinatesMapper.create(root);
        for (int i = 0; i < hierarchy.childrenId.size(); i++) {
            int child = hierarchy.childrenId.get(i);
            AbsoluteCoordinatesComponent childAbsoluteCoordinates = absoluteCoordinatesMapper.create(child);
            CoordinatesComponent childCoordinates = coordinatesMapper.get(child);
            childAbsoluteCoordinates.coordinates(absoluteCoordinates.x() + childCoordinates.x(), absoluteCoordinates.y() + childCoordinates.y(), absoluteCoordinates.z() + childCoordinates.z());
            alignTree(child);

        }
    }

    int findHighestDirtyParent(int entity){
        int dirty = entity;
        while ( (entity = hierarchyMapper.get(entity).parentId) != -1){
            if (coordinatesMapper.get(entity).isDirty()) dirty = entity;
        }
        return dirty;
    }

}
