package ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components;

import com.artemis.Component;
import com.artemis.annotations.EntityId;
import com.artemis.utils.IntBag;

public class HierarchyComponent extends Component {
    @EntityId public int parentId = -1;
    @EntityId public IntBag childrenId = new IntBag();
}
