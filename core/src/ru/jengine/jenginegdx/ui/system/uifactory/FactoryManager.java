package ru.jengine.jenginegdx.ui.system.uifactory;

import ru.jengine.jenginegdx.ui.system.widget.Widget;

import java.util.Properties;

public class FactoryManager {

    public Widget constructWidget(Properties properties) {
        properties.stringPropertyNames();
        return this.constructRecursive(properties, null);
    }

    private Widget constructRecursive(Properties properties, FactoryManager factoryManager) {
        return null;
    }

}
