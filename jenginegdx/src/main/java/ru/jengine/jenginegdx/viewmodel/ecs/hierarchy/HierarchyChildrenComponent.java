package ru.jengine.jenginegdx.viewmodel.ecs.hierarchy;

import com.artemis.utils.IntBag;
import ru.jengine.jenginegdx.viewmodel.ecs.CanDirtyPooledComponent;

public class HierarchyChildrenComponent extends CanDirtyPooledComponent {
    public IntBag children = new IntBag();

    public HierarchyChildrenComponent addChildren(int child) {
        children.add(child);
        dirty();
        return this;
    }

    public HierarchyChildrenComponent clearChildren() {
        children.clear();
        dirty();
        return this;
    }

    @Override
    protected void reset() {
        clearChildren();
    }
}
