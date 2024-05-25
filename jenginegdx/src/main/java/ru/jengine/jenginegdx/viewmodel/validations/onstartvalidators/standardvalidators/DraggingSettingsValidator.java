package ru.jengine.jenginegdx.viewmodel.validations.onstartvalidators.standardvalidators;

import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.beancontainer.annotations.Shared;
import ru.jengine.jenginegdx.Constants.Linking;
import ru.jengine.jenginegdx.Constants.UserEvents;
import ru.jengine.jenginegdx.utils.IntBagUtils;
import ru.jengine.jenginegdx.utils.exceptions.JEngineGdxException;
import ru.jengine.jenginegdx.viewmodel.ecs.draganddrop.components.DraggingSettingsComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.HierarchyComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.input.components.InputEventHandlingComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.worldholder.WorldHolder;
import ru.jengine.jenginegdx.viewmodel.validations.onstartvalidators.WorldValidator;

import static com.artemis.Aspect.all;

@Bean
@Shared
@Order(300)
public class DraggingSettingsValidator implements WorldValidator {

    public static final String DRAGGING_SETTINGS_COMPONENT_TAG = "DraggingSettingsComponent";

    @Override
    public void validate(WorldHolder worldHolder) {
        World world = worldHolder.getWorld();
        EntitySubscription draggable = world.getAspectSubscriptionManager().get(all(DraggingSettingsComponent.class));
        ComponentMapper<DraggingSettingsComponent> draggingSettingsComponentMapper = world.getMapper(DraggingSettingsComponent.class);
        ComponentMapper<HierarchyComponent> hierarchyComponentMapper = world.getMapper(HierarchyComponent.class);
        ComponentMapper<InputEventHandlingComponent> inputEventHandlingComponentMapper = world.getMapper(InputEventHandlingComponent.class);

        IntBagUtils.forEach(draggable.getEntities(), id -> {
            validateDraggingEntity(id, draggingSettingsComponentMapper, hierarchyComponentMapper);
            validateInputEventHandling(id, inputEventHandlingComponentMapper);
        });
    }

    private static void validateDraggingEntity(int settingsHolderId,
            ComponentMapper<DraggingSettingsComponent> draggingSettingsComponentMapper,
            ComponentMapper<HierarchyComponent> hierarchyComponentMapper)
    {
        DraggingSettingsComponent draggingSettings = draggingSettingsComponentMapper.get(settingsHolderId);
        int draggingEntityId = draggingSettings.getDraggingEntity();
        if (draggingEntityId == Linking.LINK_TO_NULL) {
            draggingSettings.draggingEntity(settingsHolderId);
            return;
        }

        int currentEntity = settingsHolderId;
        do {
            if (currentEntity == draggingEntityId) {
                return;
            }
            currentEntity = hierarchyComponentMapper.get(currentEntity).parentId;
        } while (currentEntity != Linking.LINK_TO_NULL);
        throw new JEngineGdxException("Dragging entity must be parent of [%s]. Actual dragging entity [%s]"
                .formatted(settingsHolderId, draggingEntityId));
    }

    private static void validateInputEventHandling(int settingsHolderId,
            ComponentMapper<InputEventHandlingComponent> inputEventHandlingComponentMapper)
    {
        if (!inputEventHandlingComponentMapper.has(settingsHolderId)) {
            Gdx.app.debug(DRAGGING_SETTINGS_COMPONENT_TAG, ("Entity [%s] has no handling any events. Drag and drop " +
                    "mechanism works with handling mouse events. Set events [%s] and [%s] to %s component").formatted(
                    settingsHolderId, UserEvents.DRAG_AND_DROP, UserEvents.DROP_TO,
                    InputEventHandlingComponent.class.getSimpleName()));
            return;
        }

        InputEventHandlingComponent inputEventHandling = inputEventHandlingComponentMapper.get(settingsHolderId);
        if (!inputEventHandling.hasHandling(UserEvents.DRAG_AND_DROP)) {
            Gdx.app.debug(DRAGGING_SETTINGS_COMPONENT_TAG, ("Entity [%s] has no handling user event [%s]. This event "
                    + "starts drag and drop mechanism. Set it to %s component").formatted(settingsHolderId,
                    UserEvents.DRAG_AND_DROP, InputEventHandlingComponent.class.getSimpleName()));
        }
        if (!inputEventHandling.hasHandling(UserEvents.DROP_TO)) {
            Gdx.app.debug(DRAGGING_SETTINGS_COMPONENT_TAG, ("Entity [%s] has no handling user event [%s]. This event "
                    + "drops entity to drag and drop container. Set it to %s component").formatted(settingsHolderId,
                    UserEvents.DROP_TO, InputEventHandlingComponent.class.getSimpleName()));
        }
    }
}
