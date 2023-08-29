package ru.jengine.jenginegdx.ui.system.uifactory;

import ru.jengine.jenginegdx.logic.WindowLogic;
import ru.jengine.jenginegdx.ui.system.widget.Widget;
import ru.jengine.jenginegdx.ui.system.widget.Window;

import java.util.Map;
public class WindowWidgetFactory extends AbstractWidgetFactory<WindowLogic, Window> {

    @Override
    protected Window makeInstanceInternal(WindowLogic obj, FactoryManager manager, Map <String, Widget> subWidgets) {
        return new Window(obj.getCoordinates(), subWidgets);
    }
}
