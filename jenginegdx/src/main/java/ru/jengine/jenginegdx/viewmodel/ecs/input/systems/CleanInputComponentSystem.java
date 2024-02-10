package ru.jengine.jenginegdx.viewmodel.ecs.input.systems;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.systems.IteratingSystem;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Order;
import ru.jengine.jenginegdx.viewmodel.ecs.input.InputComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Bean
@Order(0)
public class CleanInputComponentSystem extends IteratingSystem {
    public CleanInputComponentSystem(List<InputComponent> inputComponents) { //TODO научиться получать классы
        super(Aspect.one(convertToAspect(inputComponents)));
    }

    private static Collection<Class<? extends Component>> convertToAspect(List<InputComponent> inputComponents) {
        List<Class<? extends Component>> components = new ArrayList<>();
        for (InputComponent inputComponent : inputComponents) {
            components.add(inputComponent.getClass());
        }
        return components;
    }

    @Override
    protected void process(int entityId) {
        world.delete(entityId);
    }
}
