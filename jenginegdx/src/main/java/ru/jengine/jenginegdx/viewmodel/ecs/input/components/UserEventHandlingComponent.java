package ru.jengine.jenginegdx.viewmodel.ecs.input.components;

import com.artemis.Component;
import ru.jengine.jenginegdx.viewmodel.ecs.ExternalAddable;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@ExternalAddable
public class UserEventHandlingComponent extends Component {
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
