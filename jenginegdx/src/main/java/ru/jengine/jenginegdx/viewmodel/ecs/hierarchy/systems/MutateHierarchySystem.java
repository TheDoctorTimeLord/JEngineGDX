package ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.One;
import com.artemis.systems.IteratingSystem;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.utils.exceptions.JEngineGdxException;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.HierarchyComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.MutateHierarchyComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.MutateHierarchyRequest;


@Bean
@Order(4)
@One(MutateHierarchyComponent.class)
public class MutateHierarchySystem extends IteratingSystem {
    private ComponentMapper<HierarchyComponent> hierarchyMapper;
    private ComponentMapper<MutateHierarchyComponent> mutateHierarchyMapper;

    @Override
    protected void process(int entity) {
        MutateHierarchyComponent mutateComponent = mutateHierarchyMapper.get(entity);

        for (MutateHierarchyRequest request : mutateComponent.requestQueue) {
            switch (request.type()) {
                case addChild -> {
                    detachFromParent(request.value());
                    attachChild(request.value(), entity);
                }
                case removeChild -> detachFromParent(request.value());
                case setParent -> {
                    detachFromParent(request.value());
                    attachChild(entity, request.value());
                }
                case detachFromParent -> detachFromParent(entity);
            }
        }
        checkForCycle(entity);

        world.edit(entity).remove(mutateComponent);
    }

    private void checkForCycle(int entity) {
        int currentEntityId = entity;
        do {
            currentEntityId = hierarchyMapper.get(currentEntityId).parentId;
            if (currentEntityId == entity) {
                throw new JEngineGdxException("Created cycle in Hierarchy with entity [%s]".formatted(entity));
            }
        } while (currentEntityId != -1);
    }

    private void detachFromParent(int entity) {
        HierarchyComponent childHierarchy = hierarchyMapper.get(entity);
        if (childHierarchy.parentId == -1)
            return;
        HierarchyComponent parentHierarchy = hierarchyMapper.get(childHierarchy.parentId);
        parentHierarchy.childrenId.removeValue(entity);
        childHierarchy.parentId = -1;
    }

    private void attachChild(int child, int parent) {
        HierarchyComponent childHierarchy = hierarchyMapper.get(child);
        HierarchyComponent parentHierarchy = hierarchyMapper.get(parent);
        parentHierarchy.childrenId.add(child);
        childHierarchy.parentId = parent;
    }
}
