package xyz.lp.mq.broker.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class SpinLock implements Lock {

    AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void lock() {
        while (!atomicInteger.compareAndSet(0, 1)) {
            Thread.yield();
        }
    }

    @Override
    public void unlock() {
        atomicInteger.set(0);
    }

}
