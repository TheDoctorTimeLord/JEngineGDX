package ru.jengine.jenginegdx.viewmodel.ecs.cleaners;

import com.artemis.annotations.One;
import com.artemis.systems.IteratingSystem;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.Constants.SystemOrder;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.MouseEventComponent;

@Bean
@Order(SystemOrder.CLEARING_SYSTEMS + 3)
@One(MouseEventComponent.class) //TODO научится динамически определять эвенты для очищения
public class EventCleaner extends IteratingSystem {
    @Override
    protected void process(int entityId) {
        world.delete(entityId);
    }
}
