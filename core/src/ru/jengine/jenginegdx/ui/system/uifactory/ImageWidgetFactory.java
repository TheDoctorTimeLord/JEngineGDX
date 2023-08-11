package ru.jengine.jenginegdx.ui.system.uifactory;

import ru.jengine.jenginegdx.ui.system.ImageLogic;
import ru.jengine.jenginegdx.ui.system.WindowLogic;
import ru.jengine.jenginegdx.ui.system.widget.Image;
import ru.jengine.jenginegdx.ui.system.widget.Widget;
import ru.jengine.jenginegdx.ui.system.widget.Window;

import java.util.HashMap;
import java.util.Map;

public class ImageWidgetFactory implements WidgetFactory {

    @Override
    public Image makeInstance(Object object, FactoryManager factoryManager) {
        ImageLogic imageLogic= (ImageLogic) object;

        Map <String, Widget> subWidgets = new HashMap <>();
        if (imageLogic.getContent()!=null) {
            imageLogic.getContent().forEach((k, v) -> {
                Widget widget = factoryManager.constructRecursive(v);
                subWidgets.put(k, widget);
            });
        }
        return new Image(imageLogic.getCoordinates(), subWidgets);
    }
}
