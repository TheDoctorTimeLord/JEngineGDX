package ru.jengine.jenginegdx.viewmodel.ecs.mouse.systems;

import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.artemis.utils.IntBag;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.utils.IntBagUtils;
import ru.jengine.jenginegdx.viewmodel.camera.GameCamera;
import ru.jengine.jenginegdx.viewmodel.ecs.focus.components.FocusComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.input.systems.InputProcessingSystem;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.MouseInputTrigger;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.components.MouseEventComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.mouse.components.MouseTouchedComponent;

@Bean
@Order(250)
@All(MouseEventComponent.class)
public class MouseEventSystem extends IteratingSystem {
    private final GameCamera camera;

    private ComponentMapper<MouseEventComponent> mouseEventComponentMapper;
    private ComponentMapper<MouseTouchedComponent> mouseTouchedComponentMapper;

    @All(FocusComponent.class)
    private EntitySubscription focusedEntitySubscription;
    @All(MouseTouchedComponent.class)
    private EntitySubscription mouseTouchedEntitySubscription;
    private IntBag focusedEntities;

    public MouseEventSystem(GameCamera camera) {
        this.camera = camera;
    }

    @Override
    protected void initialize() {
        world.getSystem(InputProcessingSystem.class).registerTrigger(new MouseInputTrigger(camera));
    }

    @Override
    protected boolean checkProcessing() {
        focusedEntities = focusedEntitySubscription.getEntities();
        return !focusedEntities.isEmpty();
    }

    @Override
    protected void process(int entityId) {
        MouseEventComponent mouseEvent = mouseEventComponentMapper.get(entityId);

        IntBagUtils.forEach(mouseTouchedEntitySubscription.getEntities(), mouseTouchedComponentMapper::remove);

        IntBagUtils.forEach(focusedEntities, focusedId ->
                mouseTouchedComponentMapper.create(focusedId).setTouched(mouseEvent.getX(), mouseEvent.getY()));
    }
}
