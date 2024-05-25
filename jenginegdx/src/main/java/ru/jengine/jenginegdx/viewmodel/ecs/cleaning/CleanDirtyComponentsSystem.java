package ru.jengine.jenginegdx.viewmodel.ecs.cleaning;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.ClassesExtends;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.viewmodel.ecs.utils.CanDirtyPooledComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.utils.MarkerComponentsSystem;

import java.util.List;

@Bean
@Order(65636)
public class CleanDirtyComponentsSystem extends MarkerComponentsSystem {
    public CleanDirtyComponentsSystem(@ClassesExtends(CanDirtyPooledComponent.class) List<Class<? extends Component>> inputComponents) {
        super(inputComponents);
    }

    @Override
    protected void processEntity(int entityId, ComponentMapper<? extends Component> mapper) {
        Component component = mapper.get(entityId);
        if (component instanceof CanDirtyPooledComponent dirtyComponent) {
            dirtyComponent.clear();
        }
    }
}
