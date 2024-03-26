package ru.jengine.jenginegdx.viewmodel.ecs.hierarchy;

import com.artemis.Entity;
import com.artemis.World;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.RelativeCoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.CoordinatesComponent;

public class Hierarchy {
    private World world;

    public Hierarchy(World world) {
        this.world = world;
    }

    public void setAbsoluteCoordinates(int i, float x, float y, float z) {
        Entity entity = world.getEntity(i);
        setAbsoluteCoordinates(entity, x, y, z);
    }

    public static void setAbsoluteCoordinates(Entity entity, float x, float y, float z) {
        RelativeCoordinatesComponent rcc = entity.getComponent(RelativeCoordinatesComponent.class);
        CoordinatesComponent cc = entity.getComponent(CoordinatesComponent.class);
        rcc.coordinates(x - cc.x(), y - cc.y(), z - cc.z());
    }
}
