package ru.jengine.jenginegdx.viewmodel.ecs.text.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.One;
import com.artemis.systems.IteratingSystem;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.viewmodel.ecs.debug.components.FrameRateTextComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.text.components.TextComponent;

@Bean
@Order(2000)
@One(FrameRateTextComponent.class)
public class TextSystem extends IteratingSystem {
    private ComponentMapper<FrameRateTextComponent> frameRateTextComponentMapper;
    private ComponentMapper<TextComponent> textComponentMapper;

    @Override
    protected void process(int entityId) {
        if (frameRateTextComponentMapper.has(entityId)) {
            textComponentMapper.create(entityId).text(frameRateTextComponentMapper.get(entityId).getFrameRateText());
        }
    }
}
