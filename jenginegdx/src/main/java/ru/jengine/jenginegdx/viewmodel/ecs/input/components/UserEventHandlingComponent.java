package ru.jengine.jenginegdx.viewmodel.ecs.input.components;

import com.artemis.Component;
import ru.jengine.jenginegdx.viewmodel.ecs.CanBeFilling;
import ru.jengine.jenginegdx.viewmodel.ecs.ExternalAddable;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ExternalAddable("userEventHandling")
public class UserEventHandlingComponent extends Component implements CanBeFilling<UserEventHandlingComponent> {
    private final Map<String, String[]> eventHandling = new HashMap<>();

    public UserEventHandlingComponent addHandling(String event, String... handle) {
        eventHandling.put(event, handle);
        return this;
    }

    @Nullable
    public String[] getHandling(String event) {
        return eventHandling.get(event);
    }

    public boolean hasHandling(String expectedHandling) {
        return eventHandling.values().stream()
                .flatMap(Arrays::stream)
                .anyMatch(handling -> handling.equals(expectedHandling));
    }

    @Override
    public boolean fill(UserEventHandlingComponent other) {
        this.eventHandling.clear();
        this.eventHandling.putAll(other.eventHandling);
        return true;
    }
}
