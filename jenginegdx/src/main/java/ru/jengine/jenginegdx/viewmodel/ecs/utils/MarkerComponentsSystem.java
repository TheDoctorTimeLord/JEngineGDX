package ru.jengine.jenginegdx.viewmodel.ecs.utils;

import com.artemis.*;
import com.artemis.utils.IntBag;
import ru.jengine.utils.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class MarkerComponentsSystem extends BaseSystem {
    private final List<Class<? extends Component>> markedComponents;
    protected final Map<EntitySubscription, ComponentMapper<? extends Component>> subscriptions = new HashMap<>();

    @SuppressWarnings("unchecked")
    protected MarkerComponentsSystem(Class<? extends Annotation> marker,
                                     List<Class<?>> marketedComponents) {
        this.markedComponents = new ArrayList<>();
        for (Class<?> componentClass : marketedComponents) {
            if (AnnotationUtils.isAnnotationPresent(componentClass, marker) && Component.class.isAssignableFrom(componentClass)) {
                markedComponents.add((Class<? extends Component>) componentClass);
            }
        }
    }

    @Override
    protected void setWorld(World world) {
        super.setWorld(world);

        AspectSubscriptionManager aspectSubscriptionManager = world.getAspectSubscriptionManager();
        for (Class<? extends Component> cleanableComponent : markedComponents) {
            EntitySubscription subscription = aspectSubscriptionManager.get(Aspect.all(cleanableComponent));
            subscriptions.put(subscription, world.getMapper(cleanableComponent));
        }
        markedComponents.clear();
    }

    @Override
    protected void processSystem() {
        for (Entry<EntitySubscription, ComponentMapper<? extends Component>> entry : subscriptions.entrySet()) {
            IntBag entities = entry.getKey().getEntities();
            if (entities.isEmpty()) continue;
            processSubscription(entities, entry.getValue());
        }
    }

    protected abstract void processSubscription(IntBag entities, ComponentMapper<? extends Component> mapper);
}
