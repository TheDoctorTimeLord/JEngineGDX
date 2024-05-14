package ru.jengine.jenginegdx.viewmodel.validations.onstartvalidators.standardvalidators;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Shared;
import ru.jengine.jenginegdx.utils.IntBagUtils;
import ru.jengine.jenginegdx.viewmodel.ecs.text.components.TextPositionComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.debug.components.FpsRenderingComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.components.AbsoluteCoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.rendering.components.VisibleComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.text.components.TextStyleComponent;
import ru.jengine.jenginegdx.viewmodel.validations.onstartvalidators.WorldValidator;
import ru.jengine.jenginegdx.viewmodel.ecs.worldholder.WorldHolder;

import static com.artemis.Aspect.all;
import static ru.jengine.jenginegdx.viewmodel.ecs.debug.systems.FpsUpdatingSystem.FPS_WIDTH;

@Bean
@Shared
public class FpsRendererValidator implements WorldValidator {
    @Override
    public void validate(WorldHolder worldHolder) {
        World world = worldHolder.getWorld();

        ComponentMapper<AbsoluteCoordinatesComponent> absoluteCoordinatesComponentMapper =
                world.getMapper(AbsoluteCoordinatesComponent.class);
        ComponentMapper<VisibleComponent> visibleComponentMapper = world.getMapper(VisibleComponent.class);
        ComponentMapper<TextPositionComponent> textComponentMapper = world.getMapper(TextPositionComponent.class);
        ComponentMapper<TextStyleComponent> textStyleComponentMapper = world.getMapper(TextStyleComponent.class);

        BitmapFont font = new BitmapFont();
        font.setColor(Color.WHITE);

        IntBagUtils.forEach(world.getAspectSubscriptionManager().get(all(FpsRenderingComponent.class)).getEntities(), id -> {
            absoluteCoordinatesComponentMapper.create(id).coordinates(
                    (float) Gdx.graphics.getWidth() / 2 - FPS_WIDTH,
                    (float) Gdx.graphics.getHeight() / 2 - 20,
                    0
            );
            visibleComponentMapper.create(id);
            textComponentMapper.create(id).width(FPS_WIDTH);
            textStyleComponentMapper.create(id).font(new BitmapFont()); //TODO брать стиль из общей базы стилей
        });
    }
}
