package ru.jengine.jenginegdx.viewmodel.ecs.cleaning;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.ClassesWith;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.utils.IntBagUtils;
import ru.jengine.jenginegdx.viewmodel.ecs.MarkerComponentsSystem;

import java.util.List;

@Bean
@Order(0)
public class CleanableComponentClingingSystem extends MarkerComponentsSystem {
    public CleanableComponentClingingSystem(@ClassesWith(CleanableComponentMarker.class) List<Class<?>> inputComponents) {
        super(CleanableComponentMarker.class, inputComponents);
    }

    @Override
    protected void processSubscription(IntBag entities, ComponentMapper<? extends Component> mapper) {
        IntBagUtils.forEach(entities, mapper::remove);
    }
}
