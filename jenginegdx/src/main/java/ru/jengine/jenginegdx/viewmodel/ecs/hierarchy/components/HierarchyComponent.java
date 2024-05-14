package ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components;

import com.artemis.Component;
import com.artemis.annotations.EntityId;
import com.artemis.utils.IntBag;
import ru.jengine.jenginegdx.Constants.Linking;

public class HierarchyComponent extends Component {
    @EntityId public int parentId = Linking.LINK_TO_NULL;
    @EntityId public IntBag childrenId = new IntBag();
}
