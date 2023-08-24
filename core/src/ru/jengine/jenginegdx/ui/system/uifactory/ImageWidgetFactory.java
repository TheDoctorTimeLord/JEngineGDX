package ru.jengine.jenginegdx.ui.system.uifactory;

import ru.jengine.jenginegdx.logic.ImageLogic;
import ru.jengine.jenginegdx.logic.LogicObject;
import ru.jengine.jenginegdx.ui.system.widget.Image;
import ru.jengine.jenginegdx.ui.system.widget.Widget;

import java.util.HashMap;
import java.util.Map;
//Not needed?
//public class ImageWidgetFactory implements WidgetFactory {
//
//    @Override
//    public Image makeInstance(Object object, FactoryManager factoryManager) {
//        ImageLogic imageLogic = (ImageLogic) object;
//
//        Map <String, Widget> subWidgets = new HashMap <>();
//        if (imageLogic.getContent() != null) {
//            imageLogic.getContent().forEach((k, v) -> {
//                if (v.getClass().isInstance(LogicObject.class)) {
//                    Widget widget = factoryManager.constructRecursive((LogicObject) v);
//                    subWidgets.put(k, widget);
//                }
//
//            });
//        }
//        return new Image(imageLogic.getCoordinates(), subWidgets);
//    }
//}
