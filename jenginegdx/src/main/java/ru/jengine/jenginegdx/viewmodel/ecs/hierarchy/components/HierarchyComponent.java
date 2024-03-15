package ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components;

import com.artemis.Component;

import java.util.ArrayList;
import java.util.List;

public class HierarchyComponent extends Component {
    public int ParentId = -1;
    public List<Integer> ChildrenId = new ArrayList<>();
}
