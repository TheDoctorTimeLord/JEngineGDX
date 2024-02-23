package ru.jengine.jenginegdx.viewmodel.ecs.cleaning;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import ru.jengine.beancontainer.annotations.ClassesWith;
import ru.jengine.jenginegdx.utils.IntBagUtils;
import ru.jengine.jenginegdx.viewmodel.ecs.CanDirtyPooledComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.MarkerComponentsSystem;

import java.util.List;

public class CleanDirtyComponentsSystem extends MarkerComponentsSystem {
    public CleanDirtyComponentsSystem(@ClassesWith(CanBeDirty.class) List<Class<?>> inputComponents) {
        super(CanBeDirty.class, inputComponents);
    }

    @Override
    protected void processSubscription(IntBag entities, ComponentMapper<? extends Component> mapper) {
        IntBagUtils.forEach(entities, id -> {
            Component component = mapper.get(id);
            if (component instanceof CanDirtyPooledComponent dirtyComponent) {
                dirtyComponent.clear();
            }
        });
    }
}
