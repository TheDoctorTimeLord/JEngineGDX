package ru.jengine.jenginegdx.viewmodel.ecs.bounds;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.Constants.SystemOrder;
import ru.jengine.jenginegdx.utils.IntBagUtils;
import ru.jengine.jenginegdx.utils.figures.CombinedFigureBound;
import ru.jengine.jenginegdx.utils.figures.PositionedFigure;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.HierarchyChildrenComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.HierarchyParentComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.CoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.RotationComponent;

import javax.annotation.Nullable;
import java.util.Objects;

//@Bean
@Order(SystemOrder.CALCULATION_SYSTEMS + 1)
@All({CoordinatesComponent.class, HierarchyParentComponent.class, GlobalBoundComponent.class})
public class BoundHierarchyRecalculationSystem extends IteratingSystem { //TODO выпилить
    private ComponentMapper<BoundComponent> boundComponentMapper;
    private ComponentMapper<CoordinatesComponent> coordinatesComponentMapper;
    private ComponentMapper<RotationComponent> rotationComponentMapper;
    private ComponentMapper<GlobalBoundComponent> globalBoundComponentMapper;
    private ComponentMapper<HierarchyParentComponent> hierarchyParentComponentMapper;
    private ComponentMapper<HierarchyChildrenComponent> hierarchyChildrenComponentMapper;

    @Override
    protected void process(int entityId) {
        int currentEntityId = entityId;

        while (hasUncalculatedParent(currentEntityId)) {
            currentEntityId = hierarchyParentComponentMapper.get(currentEntityId).parent;
            recalculateRecursiveChildren(currentEntityId);
        }
    }

    private boolean hasUncalculatedParent(int entityId) {
        if (!hierarchyParentComponentMapper.has(entityId)) {
            return false;
        }

        int parentId = hierarchyParentComponentMapper.get(entityId).parent;
        return !globalBoundComponentMapper.has(parentId); //|| !globalBoundComponentMapper.get(parentId).recalcedAlready;
    }

    @Nullable
    private PositionedFigure recalculateRecursiveChildren(int childId) { //TODO сейчас мы для родителей пересчитываем баунд всегда, нужно уметь скипать пересчёт
        if (!coordinatesComponentMapper.has(childId)) {
            return null;
        }

        if (globalBoundComponentMapper.has(childId)) {
            GlobalBoundComponent childGlobalBound = globalBoundComponentMapper.get(childId);
            //if (childGlobalBound.recalcedAlready) {
                return childGlobalBound.bound;
            //}
        }

        if (hierarchyChildrenComponentMapper.has(childId)) {
            PositionedFigure[] innerFigures = IntBagUtils
                    .map(hierarchyChildrenComponentMapper.get(childId).children, this::recalculateRecursiveChildren)
                    .filter(Objects::nonNull)
                    .toArray(PositionedFigure[]::new);

            Vector3 localCoordinates = coordinatesComponentMapper.get(childId).coordinates;
            Vector2 localCoordinates2d = new Vector2(localCoordinates.x, localCoordinates.y);

            float rotation = rotationComponentMapper.has(childId)
                    ? rotationComponentMapper.get(childId).rotation
                    : RotationComponent.DEFAULT_ROTATION;

            GlobalBoundComponent globalBoundComponent = globalBoundComponentMapper.has(childId)
                    ? globalBoundComponentMapper.get(childId)
                    : globalBoundComponentMapper.create(childId);

            PositionedFigure figure = new PositionedFigure(
                    new CombinedFigureBound(innerFigures),
                    localCoordinates2d,
                    rotation
            );

            globalBoundComponent.bound = figure;
            //globalBoundComponent.recalcedAlready = true;

            return figure;
        }

        return null;
    }

    @Override
    protected void end() {
        //IntBagUtils.forEach(subscription.getEntities(), id -> globalBoundComponentMapper.get(id).recalcedAlready = false);
    }
}
