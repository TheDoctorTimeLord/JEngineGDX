package ru.jengine.jenginegdx.viewmodel.stateimporting.linking;

import com.artemis.Component;

import java.util.HashMap;
import java.util.Map;

public class EntityComponentsHolder {
    private final String entityId;
    private final Map<Class<? extends Component>, LinkField[]> linksInComponents = new HashMap<>();

    public EntityComponentsHolder(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityId() {
        return entityId;
    }

    public Iterable<Map.Entry<Class<? extends Component>, LinkField[]>> iteratingByComponents() {
        return linksInComponents.entrySet();
    }

    public void registerComponent(Class<? extends Component> componentClass, LinkField[] links) {
        linksInComponents.put(componentClass, links);
    }
}
