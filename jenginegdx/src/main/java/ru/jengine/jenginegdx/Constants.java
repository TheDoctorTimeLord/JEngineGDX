package ru.jengine.jenginegdx;

import ru.jengine.jsonconverter.JsonConverterConstants;

import java.util.Set;

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

    interface JsonFormatters {
        interface Priorities {
            int PARENT_FORMATTER = 0;
            int LINK_FORMATTER = 128;
            int COMPONENT_FORMATTER = 1024;
        }

        interface EntitySpecialFields {
            String ID = "id";
            String CHILDREN = "children";

            Set<String> ALL = Set.of(ID, CHILDREN);
        }

        interface InternalFields {
            String JAVA_CLASS = JsonConverterConstants.CLASS_PATH_FIELD;
            String PARENT = "#parent";
            String TYPE = JsonConverterConstants.TYPE;

            Set<String> ALL = Set.of(JAVA_CLASS, PARENT, TYPE);
        }

        interface JsonTypes {
            String ENTITY = "entity";
        }

        String EMPTY_LINK = "none";
    }
}
