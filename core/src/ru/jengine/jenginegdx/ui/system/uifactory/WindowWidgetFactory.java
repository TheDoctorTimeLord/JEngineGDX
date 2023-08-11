package ru.jengine.jenginegdx.ui.system.uifactory;

import ru.jengine.jenginegdx.ui.system.WindowLogic;
import ru.jengine.jenginegdx.ui.system.widget.Widget;
import ru.jengine.jenginegdx.ui.system.widget.Window;

import java.util.HashMap;
import java.util.Map;

public class WindowWidgetFactory implements WidgetFactory {

    @Override
    public Window makeInstance(Object object, FactoryManager factoryManager) {
        WindowLogic windowLogic = (WindowLogic) object;

        Map <String, Widget> subWidgets = new HashMap <>();
        windowLogic.getContent().forEach((k, v) -> {
            Widget widget = factoryManager.constructRecursive(v);
            subWidgets.put(k, widget);
        });

        return new Window(windowLogic.getCoordinates(), subWidgets);
    }
}
