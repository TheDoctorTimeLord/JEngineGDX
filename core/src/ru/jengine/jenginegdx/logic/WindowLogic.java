package ru.jengine.jenginegdx.logic;

import ru.jengine.jenginegdx.ui.system.util.Coordinates;

import java.util.Map;

public class WindowLogic extends LogicObject{

    private final int someNumberForLogic = 1;
    private final int otherNumberForLogic = 0;

    public WindowLogic(Coordinates coordinates, Map <String, Object> content) {
        super(coordinates, content);
    }

    public void resize(int newX, int newY) {
        this.coordinates.resize(newX, newY);
    }

    public int countSomething() {
        return someNumberForLogic + otherNumberForLogic;
    }
}
