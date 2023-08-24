package ru.jengine.jenginegdx;

import ru.jengine.jenginegdx.logic.ImageLogic;
import ru.jengine.jenginegdx.logic.WindowLogic;
import ru.jengine.jenginegdx.ui.system.uifactory.FactoryManager;
import ru.jengine.jenginegdx.ui.system.util.Coordinates;
import ru.jengine.jenginegdx.ui.system.widget.Widget;

import java.util.Map;

public class Example {

    public static void main(String[] args) throws Exception {
        FactoryManager factoryManager = new FactoryManager();

        ImageLogic imageSample = new ImageLogic(new Coordinates(5,6), null);
        WindowLogic windowLogic = new WindowLogic(new Coordinates(7,8), Map.of("image",imageSample));

        Widget widget = factoryManager.constructRecursive(windowLogic);
        System.out.println(widget);
    }
}
