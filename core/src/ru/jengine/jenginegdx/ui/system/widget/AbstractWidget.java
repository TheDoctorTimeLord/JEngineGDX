package ru.jengine.jenginegdx.ui.system.widget;

import ru.jengine.jenginegdx.ui.system.util.Coordinates;

import java.util.Map;

public abstract class AbstractWidget implements Widget {
    protected Coordinates coordinates;
    protected Map <String, Widget> subWidgets;

    protected AbstractWidget(Coordinates coordinates, Map <String, Widget> subWidgets) {
        this.coordinates = coordinates;
        this.subWidgets = subWidgets;
    }

    @Override
    public String toString() {
        return coordinates.toString() + '\n' + subWidgets.toString();
    }
}
