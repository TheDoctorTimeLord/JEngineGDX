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

    public <T extends Widget,L extends LogicObject> Widget constructRecursive(L object) {

        AbstractWidgetFactory <T, L> widgetFactory = new AbstractWidgetFactory<T,L>();
        return widgetFactory.makeInstance(object, this, (Class <T>) AbstractWidget.class);
    }

    private Map<Class, Class> logicToWidgetClassMap = Map.of(
            WindowLogic.class, Window.class,
            ImageLogic.class, Image.class
    );


}
