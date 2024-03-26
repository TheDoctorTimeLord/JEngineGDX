package ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.InfoHierarchyComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.MutateHierarchyComponent;


@Bean
@All({MutateHierarchyComponent.class, InfoHierarchyComponent.class})
public class MutateHierarchySystem extends IteratingSystem {

    protected ComponentMapper<InfoHierarchyComponent> mInfoMapper;
    protected ComponentMapper<MutateHierarchyComponent> mMutateMapper;

    @Override
    protected void process(int i) {
        MutateHierarchyComponent mutateComponent = mMutateMapper.get(i);
        if (!mutateComponent.isDirty()) return;

        if (mutateComponent.setParentWasRequested) {
            detachChild(i);
            attachChild(i, mutateComponent.setParentRequest);
        }

        for (int k : mutateComponent.addChildrenRequests) {
            detachChild(k);
            attachChild(k, i);
        }

        mutateComponent.clear();
    }

    private void detachChild(int i) {
        InfoHierarchyComponent child = mInfoMapper.get(i);
        if (child.ParentId == -1)
            return;
        InfoHierarchyComponent parent = mInfoMapper.get(child.ParentId);
        parent.ChildrenId.remove((Integer) i);
        child.ParentId = -1;
    }

    private void attachChild(int child, int parent) {
        InfoHierarchyComponent _child = mInfoMapper.get(child);
        InfoHierarchyComponent _parent = mInfoMapper.get(parent);
        _parent.ChildrenId.add(child);
        _child.ParentId = parent;
    }
}
