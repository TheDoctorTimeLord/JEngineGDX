package ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.HierarchyComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.RelativeCoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.CoordinatesComponent;

@Bean
@All({RelativeCoordinatesComponent.class, CoordinatesComponent.class, HierarchyComponent.class})
public class RelativeCoordinatesSystem extends IteratingSystem {
    protected ComponentMapper<RelativeCoordinatesComponent> mRC;
    protected ComponentMapper<CoordinatesComponent> mC;
    protected ComponentMapper<HierarchyComponent> mH;

    @Override
    protected void process(int i) {
        RelativeCoordinatesComponent rcp = mRC.get(i);
        HierarchyComponent hp = mH.get(i);
        CoordinatesComponent cp = mC.get(i);

        if (!rcp.isDirty()) return;
        rcp.clear();
        if (hp.ParentId == -1) {
            cp.coordinates(rcp.x(), rcp.y(), rcp.z());
            allignTree(i);
        }
        else {
            allignTree(hp.ParentId);
        }
    }

    void allignTree(int i) {
        HierarchyComponent hp = mH.get(i);
        CoordinatesComponent cp = mC.get(i);
        for (int k : hp.ChildrenId) {
            CoordinatesComponent _cp = mC.get(k);
            RelativeCoordinatesComponent _rcp = mRC.get(k);
            _cp.coordinates(cp.x() + _rcp.x(), cp.y() + _rcp.y(), cp.z() + _rcp.z());
            _rcp.clear();
            allignTree(k);
        }
    }

}
