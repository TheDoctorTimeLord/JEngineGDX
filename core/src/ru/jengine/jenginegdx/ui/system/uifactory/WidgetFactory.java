package ru.jengine.jenginegdx.ui.system.uifactory;

import ru.jengine.jenginegdx.ui.system.widget.Widget;

public interface WidgetFactory {
    public Widget makeInstance(Object object, FactoryManager factoryManager);
}
