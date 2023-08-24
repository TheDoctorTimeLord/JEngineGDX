package ru.jengine.jenginegdx.ui.system.uifactory;

import ru.jengine.jenginegdx.logic.LogicObject;
import ru.jengine.jenginegdx.ui.system.util.Coordinates;
import ru.jengine.jenginegdx.ui.system.widget.AbstractWidget;
import ru.jengine.jenginegdx.ui.system.widget.Widget;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class AbstractWidgetFactory {

    private Map<String, Widget> subWidgets;

    public void makeInstance(LogicObject obj, FactoryManager factoryManager) {

        subWidgets = new HashMap <>();
        if (obj.getContent() != null) {
            System.out.println(obj.getClass().toString());
            for(Map.Entry<String, Object> e: obj.getContent().entrySet()){

                String k = e.getKey();
                Object v = e.getValue();

                if (v instanceof LogicObject) {
                    Widget widget = factoryManager.constructRecursive((LogicObject) v);
                    subWidgets.put(k, widget);
                }
                else {
                    //TODO common widget properties
                }
            }
        }
    }

    public Map<String,Widget> getSubWidgets() {
        return subWidgets;
    }
}
