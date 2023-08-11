package ru.jengine.jenginegdx.ui.system;

import ru.jengine.jenginegdx.ui.system.util.Coordinates;

import java.util.Map;

public class WindowLogic {
    private final Coordinates coordinates;
    private final Map <String, Object> content;
    private final int someNumberForLogic = 1;
    private final int otherNumberForLogic = 0;

    public WindowLogic(Coordinates coordinates, Map <String, Object> content) {
        this.coordinates = coordinates;
        this.content = content;
    }

    public void resize(int newX, int newY) {
        this.coordinates.resize(newX, newY);
    }

    public int countSomething() {
        return someNumberForLogic + otherNumberForLogic;
    }

    public Map <String, Object> getContent() {
        return content;
    }

    public Coordinates getCoordinates() {
        return this.coordinates;
    }
}
