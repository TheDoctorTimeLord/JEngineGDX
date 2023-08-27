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

    public <T extends Widget, L extends LogicObject> Widget constructRecursive(L object) {
        return logicToWidgetClassMap.get(object.getClass()).makeInstance(object, this);
    }

    private Map <Class <?>, AbstractWidgetFactory> logicToWidgetClassMap = Map.of(
            WindowLogic.class, new WindowWidgetFactory(),
            ImageLogic.class, new ImageWidgetFactory()
    );


}
