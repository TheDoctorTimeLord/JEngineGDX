package ru.jengine.jenginegdx.viewmodel.ecs;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import ru.jengine.jenginegdx.viewmodel.ecs.transform.TransformComponent;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public abstract class SortedByZIteratingSystem extends BaseEntitySystem {
    private ComponentMapper<TransformComponent> transformComponentMapper;
    private Comparator<Integer> additionalComparator = (id1, id2) -> 0;

    protected void setAdditionalComparator(Comparator<Integer> additionalComparator) {
        this.additionalComparator = additionalComparator;
    }

    @Override
    protected final void processSystem() {
        IntBag actives = subscription.getEntities();
        int[] ids = Arrays.stream(Arrays.copyOfRange(actives.getData(), 0, actives.size()))
                .mapToObj(id -> transformComponentMapper.has(id)
                        ? Map.entry(id, transformComponentMapper.get(id).coordinates.z)
                        : Map.entry(id, Float.NEGATIVE_INFINITY))
                .sorted((e1, e2) -> {
                    int comparingResult = Float.compare(e1.getValue(), e2.getValue());
                    return comparingResult == 0
                            ? additionalComparator.compare(e1.getKey(), e2.getKey())
                            : comparingResult;
                })
                .mapToInt(Entry::getKey)
                .toArray();

        for (int i = 0, s = actives.size(); s > i; i++) {
            process(ids[i]);
        }
    }

    protected abstract void process(int entityId);
}
