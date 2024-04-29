package ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components;

import com.artemis.Component;
import com.artemis.utils.IntBag;

public class HierarchyComponent extends Component {
    public int ParentId = -1;
    public IntBag ChildrenId = new IntBag();
}
