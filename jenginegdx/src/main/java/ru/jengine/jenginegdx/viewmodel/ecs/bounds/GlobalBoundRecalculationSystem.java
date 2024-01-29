package ru.jengine.jenginegdx.viewmodel.ecs.bounds;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.Constants.SystemOrder;
import ru.jengine.jenginegdx.utils.figures.FigureBound;
import ru.jengine.jenginegdx.utils.figures.PositionedFigure;
import ru.jengine.jenginegdx.viewmodel.ecs.location.CoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.RotationComponent;

@Bean
@Order(SystemOrder.CALCULATION_SYSTEMS +  100)
@All({CoordinatesComponent.class, BoundComponent.class})
public class GlobalBoundRecalculationSystem extends IteratingSystem {
    private ComponentMapper<CoordinatesComponent> coordinatesComponentMapper;
    private ComponentMapper<RotationComponent> rotationComponentMapper;
    private ComponentMapper<BoundComponent> boundComponentMapper;
    private ComponentMapper<GlobalBoundComponent> globalBoundComponentMapper;

    @Override
    protected void process(int entityId) {
        if (globalBoundComponentMapper.has(entityId) && !isBoundChanged(entityId)) {
            return;
        }

        Vector3 localCoordinates = coordinatesComponentMapper.get(entityId).coordinates;
        Vector2 localCoordinates2d = new Vector2(localCoordinates.x, localCoordinates.y);

        float localRotation = rotationComponentMapper.has(entityId)
                ? rotationComponentMapper.get(entityId).rotation
                : RotationComponent.DEFAULT_ROTATION;

        FigureBound bound = boundComponentMapper.get(entityId).bound;

        GlobalBoundComponent globalBoundComponent = globalBoundComponentMapper.has(entityId)
                ? globalBoundComponentMapper.get(entityId)
                : globalBoundComponentMapper.create(entityId);

        globalBoundComponent.bound = new PositionedFigure(bound, localCoordinates2d, localRotation);
    }

    private boolean isBoundChanged(int entityId) {
        return coordinatesComponentMapper.get(entityId).isDirty()
                || boundComponentMapper.get(entityId).isDirty()
                || rotationComponentMapper.has(entityId) && rotationComponentMapper.get(entityId).isDirty();
    }
}
