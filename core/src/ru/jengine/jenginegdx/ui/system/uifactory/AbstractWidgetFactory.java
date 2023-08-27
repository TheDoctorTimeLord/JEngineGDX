package ru.jengine.jenginegdx.ui.system.uifactory;

import ru.jengine.jenginegdx.logic.LogicObject;
import ru.jengine.jenginegdx.ui.system.util.Coordinates;
import ru.jengine.jenginegdx.ui.system.widget.AbstractWidget;
import ru.jengine.jenginegdx.ui.system.widget.Widget;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class AbstractWidgetFactory<T extends Widget, L extends LogicObject> {

    public T makeInstance(L obj, FactoryManager factoryManager, Class<T> clazz) {

        Map <String, Widget> subWidgets = new HashMap <>();
        if (obj.getContent() != null) {
            System.out.println(obj.getClass().toString());
            obj.getContent().forEach((k, v) -> {
                if (v.getClass().isAssignableFrom(LogicObject.class)) {
                    Widget widget = factoryManager.constructRecursive((LogicObject) v);
                    subWidgets.put(k, widget);
                }
            });
        }

        T ret = null;
        try {
            ret = clazz.getConstructor(Coordinates.class, Map.class).newInstance(obj.getCoordinates(), subWidgets);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
