package ru.jengine.jenginegdx.viewmodel.ecs.hierarchy;

import com.artemis.Entity;
import com.artemis.World;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.HierarchyComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.RelativeCoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.CoordinatesComponent;

public class Hierarchy {
    private World world;

    public Hierarchy(World world){
        this.world=world;
    }
    public void setAbsoluteCoordinates(int i, float x, float y, float z){
        Entity entity = world.getEntity(i);
        setAbsoluteCoordinates(entity,x,y,z);
    }

    public void addChild(int parent, int child){
        Entity _parent = world.getEntity(parent);
        Entity _child = world.getEntity(child);
        addChild(_parent, _child);
    }

    public static void setAbsoluteCoordinates(Entity entity, float x, float y, float z){
        RelativeCoordinatesComponent rcc = entity.getComponent(RelativeCoordinatesComponent.class);
        CoordinatesComponent cc = entity.getComponent(CoordinatesComponent.class);
        rcc.coordinates(x-cc.x(),y-cc.y(),z-cc.z());
    }

    public static void addChild(Entity parent, Entity child){
        HierarchyComponent _parent = parent.getComponent(HierarchyComponent.class);
        HierarchyComponent _child = child.getComponent(HierarchyComponent.class);
        if (_child.ParentId!=-1)    removeChild(child);
        _parent.ChildrenId.add(child.getId());
        _child.ParentId = parent.getId();
    }

    public static void removeChild(Entity child){
        HierarchyComponent _child = child.getComponent(HierarchyComponent.class);
        HierarchyComponent _parent = child.getWorld().getEntity(_child.ParentId).getComponent(HierarchyComponent.class);
        _parent.ChildrenId.remove((Object)_child.ChildrenId);
        _child.ParentId = -1;
    }
}
