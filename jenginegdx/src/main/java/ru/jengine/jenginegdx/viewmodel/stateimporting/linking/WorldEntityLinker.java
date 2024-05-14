package ru.jengine.jenginegdx.viewmodel.stateimporting.linking;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.utils.IntBag;
import ru.jengine.beancontainer.annotations.Bean;
import ru.jengine.jenginegdx.Constants.JsonFormatters;
import ru.jengine.jenginegdx.utils.exceptions.JEngineGdxException;
import ru.jengine.jenginegdx.viewmodel.stateimporting.linking.LinkField.MultiLink;
import ru.jengine.jenginegdx.viewmodel.stateimporting.linking.LinkField.SingleLink;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

@Bean
public class WorldEntityLinker {
    public void linkEntities(List<String> ids, World world, EntityLinkingInfo linkingInfo,
            LinkableEntityIdMapper entityIdMapper)
    {
        for (String entityJsonId : ids) {
            EntityComponentsHolder holder = linkingInfo.getEntityComponentsHolder(entityJsonId);
            if (holder == null) continue;

            for (int worldEntityId : entityIdMapper.mapId(entityJsonId)) {
                Entity entity = world.getEntity(worldEntityId);
                for (Entry<Class<? extends Component>, LinkField[]> componentEntry : holder.iteratingByComponents()) {
                    Component component = entity.getComponent(componentEntry.getKey());
                    for (LinkField field : componentEntry.getValue()) {
                        linkComponent(component, field, entityIdMapper);
                    }
                }
            }
        }
    }

    private static void linkComponent(Component component, LinkField field, LinkableEntityIdMapper idMapper) {
        try {
            Field linkingField = component.getClass().getDeclaredField(field.getField());
            linkingField.setAccessible(true);
            if (field instanceof SingleLink single && !JsonFormatters.EMPTY_LINK.equals(single.getLink())) {
                int[] links = idMapper.mapId(single.getLink());
                if (links.length > 1) {
                    throw new JEngineGdxException("Available single link for field [%s] from [%s] but actual: %s"
                            .formatted(field, component, Arrays.toString(links)));
                }
                linkingField.setInt(component, links[0]);
            } else if (field instanceof MultiLink multi) {
                //TODO проверить корректность преобразования
                IntBag links = Arrays.stream(multi.getLinks())
                        .flatMapToInt(id -> Arrays.stream(idMapper.mapId(id)))
                        .collect(IntBag::new, IntBag::add, IntBag::addAll);
                linkingField.set(component, links);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new JEngineGdxException("Error when setting field [%s] from [%s]".formatted(field.getField(), component), e);
        }
    }
}
