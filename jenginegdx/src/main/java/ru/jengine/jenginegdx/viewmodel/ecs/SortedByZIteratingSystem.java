package ru.jengine.jenginegdx.viewmodel.ecs;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import ru.jengine.jenginegdx.utils.IntBagUtils;
import ru.jengine.jenginegdx.viewmodel.ecs.location.AbsoluteCoordinatesComponent;

import java.util.Comparator;

public abstract class SortedByZIteratingSystem extends BaseEntitySystem {
    private ComponentMapper<AbsoluteCoordinatesComponent> coordinatesComponentMapper;
    private Comparator<Integer> additionalComparator = (id1, id2) -> 0;
    private int[] sortedEntities;
    private boolean nonDirty = false;

    public SortedByZIteratingSystem() { }

    public SortedByZIteratingSystem(Aspect.Builder builder) {
        super(builder);
    }

    @Override
    protected void inserted(int entityId) {
        nonDirty = false;
    }

    @Override
    protected void removed(int entityId) {
        nonDirty = false;
    }

    protected void setAdditionalComparator(Comparator<Integer> additionalComparator) {
        this.additionalComparator = additionalComparator;
    }

    @Override
    protected final void processSystem() {
        updateSortedEntities();
        for (int id : sortedEntities) {
            process(id);
        }
    }

    protected abstract void process(int entityId);

    private void updateSortedEntities() {
        IntBag actives = subscription.getEntities();
        int[] ids = actives.getData();

        for (int i = 0, size = actives.size(); i < size; i++) {
            int id = ids[i];
            if (coordinatesComponentMapper.has(id) && coordinatesComponentMapper.get(id).isDirty()) {
                nonDirty = false;
                break;
            }
        }

        if (sortedEntities != null && nonDirty) {
            return;
        }

        sortedEntities =
                IntBagUtils.map(actives, id -> coordinatesComponentMapper.has(id)
                        ? new OrderedEntity(id, coordinatesComponentMapper.get(id).getCoordinates().z)
                        : new OrderedEntity(id, Float.NEGATIVE_INFINITY))
                .sorted((e1, e2) -> {
                    int comparingResult = Float.compare(e1.order, e2.order);
                    return comparingResult == 0
                            ? additionalComparator.compare(e1.id, e2.id)
                            : comparingResult;
                })
                .mapToInt(e -> e.id)
                .toArray();
        nonDirty = true;
    }

    private record OrderedEntity(int id, float order) { }
}
