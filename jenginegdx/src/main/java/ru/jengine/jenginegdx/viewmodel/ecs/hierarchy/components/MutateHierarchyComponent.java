package ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components;

import com.badlogic.gdx.utils.Queue;
import ru.jengine.jenginegdx.viewmodel.ecs.CanDirtyPooledComponent;

public class MutateHierarchyComponent extends CanDirtyPooledComponent {
    public int setParentRequest = -1;
    public boolean setParentWasRequested = false;

    public Queue<Integer> addChildrenRequests = new Queue<>();

    public MutateHierarchyComponent setParent(int i) {
        setParentRequest = i;
        setParentWasRequested = true;
        dirty();
        return this;
    }

    public MutateHierarchyComponent addChild(int... i) {
        for (int k : i) {
            addChildrenRequests.addLast(k);
        }
        dirty();
        return this;
    }

    @Override
    public void clear() {
        super.clear();
        setParentWasRequested = false;
        addChildrenRequests.clear();
    }
}
