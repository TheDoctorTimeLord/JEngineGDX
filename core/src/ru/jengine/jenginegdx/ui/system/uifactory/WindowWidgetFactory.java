package ru.jengine.jenginegdx.ui.system.uifactory;

import ru.jengine.jenginegdx.logic.LogicObject;
import ru.jengine.jenginegdx.logic.WindowLogic;
import ru.jengine.jenginegdx.ui.system.widget.Widget;
import ru.jengine.jenginegdx.ui.system.widget.Window;

import java.util.HashMap;
import java.util.Map;
//Not needed?
//public class WindowWidgetFactory implements WidgetFactory {
//
////    @Override
////    public Window makeInstance(Object object, FactoryManager factoryManager) {
////        WindowLogic windowLogic = (WindowLogic) object;
////
////        Map <String, Widget> subWidgets = new HashMap <>();
////        windowLogic.getContent().forEach((k, v) -> {
////            if (v.getClass().isInstance(LogicObject.class)) {
////                Widget widget = factoryManager.constructRecursive((LogicObject) v);
////                subWidgets.put(k, widget);
////            }
////        });
////
////        return new Window(windowLogic.getCoordinates(), subWidgets);
//    }
//}
