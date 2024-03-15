package ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components;

import com.artemis.PooledComponent;
import com.badlogic.gdx.utils.Queue;

public class MutateHierarchyComponent extends PooledComponent {
    public Queue<MutateHierarchyRequest> requestQueue = new Queue<>();

    public MutateHierarchyComponent setParent(int newParent) {
        requestQueue.addLast
                (new MutateHierarchyRequest(MutateHierarchyRequestType.SetParent, newParent));
        return this;
    }

    public MutateHierarchyComponent addChild(int... newChildren) {
        for (int child : newChildren) {
            requestQueue.addLast
                    (new MutateHierarchyRequest(MutateHierarchyRequestType.AddChild, child));
        }
        return this;
    }

    public MutateHierarchyComponent detachFromParent(){
        requestQueue.addLast
                (new MutateHierarchyRequest(MutateHierarchyRequestType.DetachFromParent, -1));
        return this;
    }

    public MutateHierarchyComponent removeChild(int... children) {
        for (int child : children) {
            requestQueue.addLast
                    (new MutateHierarchyRequest(MutateHierarchyRequestType.RemoveChild, child));
        }
        return this;
    }

    @Override
    protected void reset() {
        requestQueue.clear();
    }
}
