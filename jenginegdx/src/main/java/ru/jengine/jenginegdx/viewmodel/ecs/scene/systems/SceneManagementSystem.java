package ru.jengine.jenginegdx.viewmodel.ecs.scene.systems;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.EntitySubscription;
import com.artemis.annotations.All;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.beancontainer.annotations.PostConstruct;
import ru.jengine.jenginegdx.utils.IntBagUtils;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.SinglePostHandler;
import ru.jengine.jenginegdx.viewmodel.ecs.eventdispatching.systems.EventBus;
import ru.jengine.jenginegdx.viewmodel.ecs.scene.ChangeSceneEvent;
import ru.jengine.jenginegdx.viewmodel.ecs.scene.components.OnCurrentSceneComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.scene.components.SceneComponent;

@Bean
@Order(-10)
public class SceneManagementSystem extends BaseSystem {
    private int currScene = 0;
    private int nextScene = 0;

    private ComponentMapper<OnCurrentSceneComponent> onSceneComponentComponentMapper;
    private ComponentMapper<SceneComponent> sceneComponentComponentMapper;
    @All(OnCurrentSceneComponent.class)
    private EntitySubscription onSceneSubscription;
    @All(SceneComponent.class)
    private EntitySubscription sceneSubscription;

    private ChangeSceneListener listener;

    @Override
    protected void processSystem() {
        if (currScene != nextScene) {
            IntBagUtils.forEach(onSceneSubscription.getEntities(), id -> onSceneComponentComponentMapper.remove(id));
            IntBagUtils.forEach(sceneSubscription.getEntities(), id -> {
                SceneComponent component = sceneComponentComponentMapper.get(id);
                if (component.getSceneId() == nextScene) {
                    onSceneComponentComponentMapper.create(id);
                }
            });
            currScene = nextScene;
        }
    }

    @PostConstruct
    private void initialize(EventBus eventBus) {
        listener = new ChangeSceneListener();
        eventBus.registerHandler(listener);
    }

    private class ChangeSceneListener extends SinglePostHandler<ChangeSceneEvent> {
        @Override
        public void handle(ChangeSceneEvent changeSceneEvent) {
            nextScene = changeSceneEvent.nextScene();
        }
    }

}
