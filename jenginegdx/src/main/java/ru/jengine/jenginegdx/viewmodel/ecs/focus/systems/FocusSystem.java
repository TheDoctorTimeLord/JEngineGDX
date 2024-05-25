package ru.jengine.jenginegdx.viewmodel.ecs.focus.systems;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.annotations.All;
import com.artemis.utils.IntBag;
import com.badlogic.gdx.math.Vector3;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.ClassesExtends;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.utils.IntBagUtils;
import ru.jengine.jenginegdx.viewmodel.ecs.focus.components.FocusComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.focus.components.FocusSettingsComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.input.InputWithCoordinate;
import ru.jengine.jenginegdx.viewmodel.ecs.input.components.InputBoundComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.input.components.InputComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.input.components.InputEventHandlingComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.components.AbsoluteCoordinatesComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.location.components.RotationComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.utils.MarkerComponentsSystem;

import java.util.List;

@Bean
@Order(250)
public class FocusSystem extends MarkerComponentsSystem {
    private ComponentMapper<FocusSettingsComponent> focusSettingsComponentMapper;
    private ComponentMapper<FocusComponent> focusComponentMapper;
    private ComponentMapper<AbsoluteCoordinatesComponent> absoluteCoordinatesComponentMapper;
    private ComponentMapper<InputBoundComponent> inputBoundComponentMapper;
    private ComponentMapper<RotationComponent> rotationComponentMapper;

    @All(FocusComponent.class)
    private EntitySubscription focusedEntitiesSubscription;
    @All(FocusSettingsComponent.class)
    private EntitySubscription canBeFocusedEntitiesSubscription;
    @All({AbsoluteCoordinatesComponent.class, InputBoundComponent.class, FocusSettingsComponent.class, InputEventHandlingComponent.class})
    private EntitySubscription clickableElementsSubscription;

    public FocusSystem(@ClassesExtends(InputComponent.class) List<Class<? extends Component>> inputComponents) {
        super(inputComponents);
    }

    @Override
    protected void processEntity(int entityId, ComponentMapper<? extends Component> mapper) {
        InputComponent input = (InputComponent) mapper.get(entityId);
        boolean isFocusLost = loseFocus(input);
        if (isFocusLost) {
            captureFocus(input);
        }
    }

    private boolean loseFocus(InputComponent input) {
        IntBag focusedEntities = focusedEntitiesSubscription.getEntities();
        if (focusedEntities.isEmpty() || !input.isInteractionEvent()) {
            return true;
        }

        return IntBagUtils.map(focusedEntities, focused -> {
            boolean isFocusLost = !focusSettingsComponentMapper.has(focused)
                    || focusSettingsComponentMapper.get(focused).getFocusLoseHandler().isFocusLost(focused, input);

            if (isFocusLost) {
                focusComponentMapper.remove(focused);
            }
            return isFocusLost;
        })
        .allMatch(isFocusLoosed -> isFocusLoosed);
    }

    private void captureFocus(InputComponent input) {
        if (!input.isInteractionEvent() || !(input instanceof InputWithCoordinate inputWithCoordinate)) {
            return;
        }

        float x = inputWithCoordinate.getX();
        float y = inputWithCoordinate.getY();

        IntBagUtils.forEach(canBeFocusedEntitiesSubscription.getEntities(), entityId -> {
            AbsoluteCoordinatesComponent absoluteCoordinates = absoluteCoordinatesComponentMapper.get(entityId);
            Vector3 coordinates = absoluteCoordinates.getCoordinates();
            InputBoundComponent inputBound = inputBoundComponentMapper.get(entityId);
            float rotation = rotationComponentMapper.has(entityId)
                    ? rotationComponentMapper.get(entityId).getRotation()
                    : RotationComponent.DEFAULT_ROTATION;

            if (inputBound.inBound(x, y, coordinates.x, coordinates.y, rotation)) {
                focusComponentMapper.create(entityId);
            }
        });
    }
}
