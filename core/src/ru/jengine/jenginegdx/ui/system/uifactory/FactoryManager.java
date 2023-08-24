package ru.jengine.jenginegdx.ui.system.uifactory;

import ru.jengine.jenginegdx.logic.ImageLogic;
import ru.jengine.jenginegdx.logic.LogicObject;
import ru.jengine.jenginegdx.logic.WindowLogic;
import ru.jengine.jenginegdx.ui.system.widget.AbstractWidget;
import ru.jengine.jenginegdx.ui.system.widget.Image;
import ru.jengine.jenginegdx.ui.system.widget.Widget;
import ru.jengine.jenginegdx.ui.system.widget.Window;

import java.util.Map;

public class FactoryManager {

    public <L extends LogicObject> Widget constructRecursive(L object) {

        AbstractWidgetFactory widgetFactory = new AbstractWidgetFactory();
        widgetFactory.makeInstance(object, this);

        //TODO class mapping
        //TODO remove following
        return new Window(object.getCoordinates(), widgetFactory.getSubWidgets());
    }

}
