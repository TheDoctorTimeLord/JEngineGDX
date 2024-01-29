package ru.jengine.jenginegdx.viewmodel.ecs.userevents;

import com.artemis.Component;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class UserEventHandlingComponent extends Component { //TODO всё же добавить инкапсуляцию компонентам
    private final Map<String, String> eventHandling = new HashMap<>();

    public UserEventHandlingComponent addHandling(String event, String handle) {
        eventHandling.put(event, handle);
        return this;
    }

    @Nullable
    public String getHandling(String event) {
        return eventHandling.get(event);
    }
}
