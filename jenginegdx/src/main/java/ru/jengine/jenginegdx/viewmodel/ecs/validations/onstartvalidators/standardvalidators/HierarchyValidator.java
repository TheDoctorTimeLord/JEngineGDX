package ru.jengine.jenginegdx.viewmodel.ecs.validations.onstartvalidators.standardvalidators;

import com.artemis.*;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.beancontainer.annotations.Shared;
import ru.jengine.jenginegdx.utils.IntBagUtils;
import ru.jengine.jenginegdx.utils.exceptions.JEngineGdxException;
import ru.jengine.jenginegdx.viewmodel.ecs.hierarchy.components.HierarchyComponent;
import ru.jengine.jenginegdx.viewmodel.ecs.validations.onstartvalidators.WorldValidator;
import ru.jengine.jenginegdx.viewmodel.ecs.worldholder.WorldHolder;

import static com.artemis.Aspect.all;

@Bean
@Shared
public class HierarchyValidator implements WorldValidator {
    @Override
    public void validate(WorldHolder worldHolder) {
        World world = worldHolder.getWorld();
        AspectSubscriptionManager subscriptionManager = world.getAspectSubscriptionManager();

        ComponentMapper<HierarchyComponent> hierarchyComponentMapper = world.getMapper(HierarchyComponent.class);

        subscriptionManager.process();
        EntitySubscription subscription = subscriptionManager.get(all(HierarchyComponent.class));

        IntBagUtils.forEach(subscription.getEntities(), id -> {
            HierarchyComponent checkedHierarchy = hierarchyComponentMapper.get(id);

            if (checkedHierarchy.parentId != HierarchyComponent.NONE_LINK) {
                HierarchyComponent parentHierarchy = createOrGet(checkedHierarchy.parentId, hierarchyComponentMapper);
                if (!parentHierarchy.childrenId.contains(id)) {
                    parentHierarchy.childrenId.add(id);
                }
            }
            if (!checkedHierarchy.childrenId.isEmpty()) {
                IntBagUtils.forEach(checkedHierarchy.childrenId, childId -> {
                    HierarchyComponent childHierarchy = createOrGet(childId, hierarchyComponentMapper);
                    if (childHierarchy.parentId != id) {
                        if (childHierarchy.parentId != HierarchyComponent.NONE_LINK) {
                            throw new JEngineGdxException("Entity [%d] was child of [%d] but [%d] has parent of [%d]"
                                    .formatted(childId, id, childId, childHierarchy.parentId));
                        }
                        childHierarchy.parentId = id;
                    }
                });
            }
        });
    }

    private static <T extends Component> T createOrGet(int id, ComponentMapper<T> mapper) {
        return mapper.has(id) ? mapper.get(id) : mapper.create(id);
    }
}
