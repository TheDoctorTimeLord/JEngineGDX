package ru.jengine.jenginegdx.view.focus;

import com.artemis.ComponentMapper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.viewmodel.ecs.focus.components.FocusComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.input.components.InputBoundComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.components.AbsoluteCoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.rendering.RenderSubsystem;

@Bean
@Order(50)
public class FocusedRenderSubsystem extends RenderSubsystem {
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    private ComponentMapper<AbsoluteCoordinatesComponent> absoluteCoordinatesComponentMapper;
    private ComponentMapper<InputBoundComponent> inputBoundComponentMapper;
    private ComponentMapper<FocusComponent> focusComponentMapper;

    @Override
    public void render(int entityId, Batch batch) {
        if (!focusComponentMapper.has(entityId) || !absoluteCoordinatesComponentMapper.has(entityId)
                || !inputBoundComponentMapper.has(entityId))
        {
            return;
        }

        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeType.Filled);

        Vector3 coordinates = absoluteCoordinatesComponentMapper.get(entityId).getCoordinates();
        InputBoundComponent inputBound = inputBoundComponentMapper.get(entityId);

        float x0 = coordinates.x;
        float y0 = coordinates.y;

        float x1 = coordinates.x;
        float y1 = coordinates.y + inputBound.getHeight();

        float x2 = coordinates.x + inputBound.getWidth();
        float y2 = coordinates.y + inputBound.getHeight();

        shapeRenderer.triangle(x0, y0, x1, y1, x2, y2, Color.YELLOW, Color.YELLOW, Color.YELLOW);

        shapeRenderer.end();
        batch.begin();
    }
}
