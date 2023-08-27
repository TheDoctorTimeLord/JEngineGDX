package ru.jengine.jenginegdx.ui.system.uifactory;

import ru.jengine.jenginegdx.logic.ImageLogic;
import ru.jengine.jenginegdx.logic.LogicObject;
import ru.jengine.jenginegdx.ui.system.widget.Image;
import ru.jengine.jenginegdx.ui.system.widget.Widget;

import java.util.HashMap;
import java.util.Map;
public class ImageWidgetFactory extends AbstractWidgetFactory<ImageLogic, Image> {

    @Override
    protected Image makeInstanceInternal(ImageLogic obj, FactoryManager manager, Map <String, Widget> subWidgets) {
        return new Image(obj.getCoordinates(), subWidgets);
    }
}
