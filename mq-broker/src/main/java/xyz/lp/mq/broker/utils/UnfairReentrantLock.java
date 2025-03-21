package xyz.lp.mq.broker.utils;

import java.util.concurrent.locks.ReentrantLock;

public class UnfairReentrantLock implements Lock {

    private ReentrantLock lock = new ReentrantLock(false);

    @Override
    public void lock() {
        lock.lock();
    }

    @Override
    public void unlock() {
        lock.unlock();
    }
}
