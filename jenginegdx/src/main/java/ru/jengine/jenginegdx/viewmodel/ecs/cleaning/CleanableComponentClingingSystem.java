package ru.jengine.jenginegdx.viewmodel.ecs.cleaning;

import com.artemis.*;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.utils.IntBagUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Bean
@Order(0)
public class CleanableComponentClingingSystem extends BaseSystem {
    private final List<Class<? extends Component>> cleanableComponents;
    private final Map<EntitySubscription, Class<? extends Component>> subscriptions = new HashMap<>();

    public CleanableComponentClingingSystem(List<Component> inputComponents) { //TODO научиться получать классы
        this.cleanableComponents = convertToComponentClasses(inputComponents);
    }

    private static List<Class<? extends Component>> convertToComponentClasses(List<Component> inputComponents) {
        List<Class<? extends Component>> components = new ArrayList<>();
        for (Component component : inputComponents) {
            components.add(component.getClass());
        }
        return components;
    }

    @Override
    protected void setWorld(World world) {
        super.setWorld(world);

        AspectSubscriptionManager aspectSubscriptionManager = world.getAspectSubscriptionManager();
        for (Class<? extends Component> cleanableComponent : cleanableComponents) {
            EntitySubscription subscription = aspectSubscriptionManager.get(Aspect.all(cleanableComponent));
            subscriptions.put(subscription, cleanableComponent);
        }
    }

    @Override
    public void processSystem() {
        for (Entry<EntitySubscription, Class<? extends Component>> subscription : subscriptions.entrySet()) {
            IntBagUtils.forEach(subscription.getKey().getEntities(), id ->
                    world.getMapper(subscription.getValue()).remove(id));
        }
    }
}
