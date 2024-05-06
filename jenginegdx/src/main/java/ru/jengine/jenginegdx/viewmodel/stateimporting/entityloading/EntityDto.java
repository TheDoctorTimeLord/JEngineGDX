package ru.jengine.jenginegdx.viewmodel.stateimporting.entityloading;

import com.artemis.Component;

import java.util.List;

public class EntityDto {
    private String id;
    private List<Component> components;
    private List<EntityDto> children = List.of();

    public String getId() {
        return id;
    }

    public List<Component> getComponents() {
        return components;
    }

    public List<EntityDto> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityDto entityDto = (EntityDto) o;

        if (!id.equals(entityDto.id)) return false;
        if (!components.equals(entityDto.components)) return false;
        return children.equals(entityDto.children);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + components.hashCode();
        result = 31 * result + children.hashCode();
        return result;
    }
}
