package ru.jengine.jenginegdx.viewmodel.stateimporting.dto;

import com.artemis.Component;

import java.util.List;

public class EntityJsonDto {
    private String id;
    private List<Component> components;

    public String getId() {
        return id;
    }

    public List<Component> getComponents() {
        return components;
    }
}
