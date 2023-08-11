package ru.jengine.jenginegdx.ui.system.uifactory;

import ru.jengine.jenginegdx.ui.system.ImageLogic;
import ru.jengine.jenginegdx.ui.system.WindowLogic;
import ru.jengine.jenginegdx.ui.system.widget.Widget;

import java.util.Properties;

public class FactoryManager {

    public Widget constructRecursive(Object object) {

        WidgetFactory factory = makeFactory(object);
        return factory.makeInstance(object, this);
    }

    private WidgetFactory makeFactory(Object object) {
        if (object.getClass().equals(WindowLogic.class)){
            return new WindowWidgetFactory();
        }
        if (object.getClass().equals(ImageLogic.class)){
            return new ImageWidgetFactory();
        }
        return null;
    }

}
