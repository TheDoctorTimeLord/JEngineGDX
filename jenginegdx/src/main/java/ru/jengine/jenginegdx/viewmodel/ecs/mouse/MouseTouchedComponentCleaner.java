package ru.jengine.jenginegdx.viewmodel.ecs.mouse;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;

@Bean
@Order(0)
@All(MouseTouchedComponent.class)
public class MouseTouchedComponentCleaner extends IteratingSystem {
    private ComponentMapper<MouseTouchedComponent> mouseTouchedComponentMapper;

    @Override
    protected void process(int entityId) {
        mouseTouchedComponentMapper.remove(entityId);
    }
}
