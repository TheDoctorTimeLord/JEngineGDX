package ru.jengine.jenginegdx.ui.system.uifactory;

import ru.jengine.jenginegdx.logic.LogicObject;
import ru.jengine.jenginegdx.ui.system.widget.Widget;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWidgetFactory<L extends LogicObject, T extends Widget> {

    public T makeInstance(L obj, FactoryManager factoryManager) {
        Map <String, Widget> subWidgets = new HashMap <>();
        if (obj.getContent() != null) {
            System.out.println(obj.getClass().toString());
            for (LogicObject v : obj.getContent()) {
                Widget widget = factoryManager.constructRecursive(v);
                subWidgets.put(v.getName(), widget);
            }
        }
        return makeInstanceInternal(obj, factoryManager, subWidgets);
    }

    protected abstract T makeInstanceInternal(L obj, FactoryManager manager, Map <String, Widget> subWidgets);
}
