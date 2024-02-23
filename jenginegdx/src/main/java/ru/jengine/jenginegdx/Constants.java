package ru.jengine.jenginegdx;

public interface Constants {
    interface Contexts {
        String JENGINE = "jenginegdx";
    }

    interface InputEvents {
        String MOUSE_MOVE = "mouse_Move";
        String MOUSE_TOUCH_UP = "mouse_TouchUp";
        String MOUSE_TOUCH_DOWN = "mouse_TouchDown";
        String MOUSE_START_DRAGGING = "mouse_StartDragging";
        String MOUSE_DRAGGING = "mouse_Dragging";
        String MOUSE_DRAGGED_TO = "mouse_DraggedTo";
    }

    interface UserEvents {
        String DRAG_AND_DROP = "dragAndDrop";
        String DROP_TO = "dropTo";
    }
}
