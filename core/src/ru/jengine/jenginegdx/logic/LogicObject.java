package ru.jengine.jenginegdx.logic;

import ru.jengine.jenginegdx.ui.system.util.Coordinates;

import java.util.List;

//Логические объекты являются внешними для модулю ui, но подчиняются установленной иерархии объектов

public abstract class LogicObject {
    protected final Coordinates coordinates;
    protected final List<? extends LogicObject> content;
    protected final String name;


    protected LogicObject(String name, Coordinates coordinates, List<? extends LogicObject> content) {
        this.name = name;
        this.coordinates = coordinates;
        this.content = content;
    }

    public List<? extends LogicObject> getContent() {
        return content;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }


    public String getName() {
        return this.name;
    }
}
