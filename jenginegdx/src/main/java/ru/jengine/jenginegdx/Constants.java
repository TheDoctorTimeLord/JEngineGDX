package ru.jengine.jenginegdx;

public interface Constants {
    interface SystemOrder { //TODO перепродумать порядок систем
        int INPUT_PROCESSING_SYSTEMS = 0;
        int INTERNAL_SYSTEMS = 256;
        int VIEW_MODEL_SYSTEMS = 1024;
        int CALCULATION_SYSTEMS = 2048;
        int CLEARING_SYSTEMS = 3072;
        int RENDERING_SYSTEMS = 4096;
    }

    interface Contexts {
        String JENGINE = "jenginegdx";
    }

    interface InputEvents {
        String MOUSE_MOVE = "mouseMove";
        String MOUSE_TOUCH_UP = "mouseTouchUp";
        String MOUSE_TOUCH_DOWN = "mouseTouchDown";
        String MOUSE_DRAGGING = "mouseDragging";
        String MOUSE_DRAGGED_TO = "mouseDraggedTo";
    }

    interface UserEvents {
        String DRAG_AND_DROP = "dragAndDrop";
    }
}
