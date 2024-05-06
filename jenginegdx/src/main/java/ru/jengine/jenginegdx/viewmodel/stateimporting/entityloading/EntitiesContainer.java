package ru.jengine.jenginegdx.viewmodel.stateimporting.entityloading;

import ru.jengine.jenginegdx.viewmodel.stateimporting.linking.EntityLinkingInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntitiesContainer {
    private Map<String, EntityDto> prototypes;
    private List<EntityDto> entities;
    private transient EntityLinkingInfo entityLinkingInfo;

    public Map<String, EntityDto> getPrototypes() {
        return prototypes == null ? Map.of() : new HashMap<>(prototypes);
    }
    public List<EntityDto> getEntities() {
        return entities == null ? List.of() : new ArrayList<>(entities);
    }

    public EntityLinkingInfo getEntityLinkingInfo() {
        return entityLinkingInfo;
    }

    public void setEntityLinkingInfo(EntityLinkingInfo entityLinkingInfo) {
        this.entityLinkingInfo = entityLinkingInfo;
    }
}
