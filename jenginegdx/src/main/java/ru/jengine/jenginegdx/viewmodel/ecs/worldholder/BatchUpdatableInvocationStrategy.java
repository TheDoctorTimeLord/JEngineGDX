package ru.jengine.jenginegdx.viewmodel.ecs.worldholder;

import com.artemis.InvocationStrategy;

public class BatchUpdatableInvocationStrategy extends InvocationStrategy {
    public void updateBatch() {
        updateEntityStates();
    }
}
