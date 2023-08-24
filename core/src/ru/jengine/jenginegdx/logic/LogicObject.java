package ru.jengine.jenginegdx.logic;

import ru.jengine.jenginegdx.ui.system.util.Coordinates;

import java.util.Map;

//Логические объекты являются внешними для модулю ui, но подчиняются установленной иерархии объектов

public abstract class LogicObject {
    protected final Coordinates coordinates;
    protected final Map <String, Object> content;


    protected LogicObject(Coordinates coordinates, Map <String, Object> content) {
        this.coordinates = coordinates;
        this.content = content;
    }

    public Map <String, Object> getContent() {
        return content;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }
}
