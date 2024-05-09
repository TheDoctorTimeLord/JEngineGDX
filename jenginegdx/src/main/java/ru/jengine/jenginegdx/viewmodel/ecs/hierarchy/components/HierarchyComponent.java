package ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components;

import com.artemis.Component;
import com.artemis.annotations.EntityId;
import com.artemis.utils.IntBag;

public class HierarchyComponent extends Component {
    public static final int NONE_LINK = -1;

    @EntityId public int parentId = NONE_LINK;
    @EntityId public IntBag childrenId = new IntBag();
}
