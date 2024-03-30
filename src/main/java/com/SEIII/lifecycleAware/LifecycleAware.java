package com.SEIII.lifecycleAware;

import java.util.concurrent.atomic.AtomicReference;

public abstract class LifecycleAware {
    private final AtomicReference<LifecycleState> state = new AtomicReference<>(LifecycleState.IDLE);


    public void start() {
        state.compareAndSet(LifecycleState.IDLE, LifecycleState.START);
    }

    public void stop() {
        state.compareAndSet(LifecycleState.START, LifecycleState.STOP);
    }

    public LifecycleState getLifecycleState() {
        return state.get();
    }
}
