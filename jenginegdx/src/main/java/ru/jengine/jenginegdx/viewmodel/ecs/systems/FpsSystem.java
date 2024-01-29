package ru.jengine.jenginegdx.viewmodel.ecs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.Constants.SystemOrder;
import ru.jengine.jenginegdx.viewmodel.ecs.label.LabelComponent;

@Bean
@Order(SystemOrder.VIEW_MODEL_SYSTEMS)
@All({LabelComponent.class, FpsMarkerComponent.class})
public class FpsSystem extends IteratingSystem {
    private ComponentMapper<LabelComponent> labelComponentMapper;

    @Override
    protected void process(int entityId) {
        int fps = Gdx.graphics.getFramesPerSecond();
        labelComponentMapper.get(entityId).text(String.valueOf(fps));
    }
}
