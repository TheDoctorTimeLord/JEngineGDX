package ru.jengine.jenginegdx.ui.system.widget;

public enum WidgetTypes {
    LABEL("label"),
    IMAGE("image"),
    BUTTON("button"),
    WINDOW("window");

    private final String widget;

    WidgetTypes(String widget) {
        this.widget = widget;
    }

    public String getWidget() {
        return widget;
    }
}
