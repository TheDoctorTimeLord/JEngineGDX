package ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.One;
import com.artemis.systems.IteratingSystem;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.HierarchyComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.MutateHierarchyComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.MutateHierarchyRequest;


@Bean
@One(MutateHierarchyComponent.class)
public class MutateHierarchySystem extends IteratingSystem {

    protected ComponentMapper<HierarchyComponent> hierarchyMapper;
    protected ComponentMapper<MutateHierarchyComponent> mutateHierarchyMapper;

    @Override
    protected void process(int entity) {
        MutateHierarchyComponent mutateComponent = mutateHierarchyMapper.get(entity);

        for (MutateHierarchyRequest request : mutateComponent.requestQueue) {
            switch (request.type()) {
                case AddChild -> {
                    detachFromParent(request.value());
                    attachChild(request.value(), entity);
                }
                case RemoveChild -> detachFromParent(request.value());
                case SetParent -> {
                    detachFromParent(request.value());
                    attachChild(entity, request.value());
                }
                case DetachFromParent -> detachFromParent(entity);
            }
        }
        checkForCycle(entity);

        world.edit(entity).remove(mutateComponent);
    }

    private void checkForCycle(int entity) {
        int t = entity;
        do {
            t = hierarchyMapper.get(t).ParentId;
            if (t == entity) throw new RuntimeException("Created cycle in Hierarchy");
        } while (t != -1);
    }

    private void detachFromParent(int entity) {
        HierarchyComponent childHierarchy = hierarchyMapper.get(entity);
        if (childHierarchy.ParentId == -1)
            return;
        HierarchyComponent parentHierarchy = hierarchyMapper.get(childHierarchy.ParentId);
        parentHierarchy.ChildrenId.remove((Integer) entity);
        childHierarchy.ParentId = -1;
    }

    private void attachChild(int child, int parent) {
        HierarchyComponent childHierarchy = hierarchyMapper.get(child);
        HierarchyComponent parentHierarchy = hierarchyMapper.get(parent);
        parentHierarchy.ChildrenId.add(child);
        childHierarchy.ParentId = parent;
    }
}
