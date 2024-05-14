package ru.jengine.jenginegdx.viewmodel.stateimporting.linking;

import com.artemis.utils.IntBag;
import ru.jengine.jenginegdx.utils.exceptions.JEngineGdxException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LinkableEntityIdMapper {
    private final Map<String, IntBag> entitiesById = new HashMap<>();

    public void registerEntity(String idInJson, int idInWorld) {
        entitiesById.computeIfAbsent(idInJson, id -> new IntBag()).add(idInWorld);
    }

    public int[] mapId(String key) {
        IntBag ids = entitiesById.get(key);
        if (ids == null) {
            throw new JEngineGdxException("For entity id [%s] is not registered world entity id".formatted(key));
        }
        return Arrays.copyOf(ids.getData(), ids.size());
    }
}
