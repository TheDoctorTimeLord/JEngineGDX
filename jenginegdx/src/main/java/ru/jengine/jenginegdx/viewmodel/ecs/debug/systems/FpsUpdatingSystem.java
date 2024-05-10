package ru.jengine.jenginegdx.viewmodel.ecs.debug.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.viewmodel.ecs.debug.components.FpsRenderingComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.debug.components.FrameRateTextComponent;

@Bean
@Order(512)
@All(FpsRenderingComponent.class)
public class FpsUpdatingSystem extends IteratingSystem {
    public static final float FPS_WIDTH = 70;

    private ComponentMapper<FrameRateTextComponent> frameRateTextComponentMapper;

    @Override
    protected void process(int entityId) {
        FrameRateTextComponent frameRateText = frameRateTextComponentMapper.create(entityId);
        frameRateText.frameRateText(Gdx.graphics.getFramesPerSecond());
    }
}
