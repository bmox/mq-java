package xyz.lp.mq.broker.config;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CommonThreadPoolConfig {

    public static ThreadPoolExecutor FLUSH_TOPIC_EXECUTOR = new ThreadPoolExecutor(
            1, 1, 30L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(10),
            r -> {
                Thread thread = new Thread(r);
                thread.setName("flush-topic-executor");
                return thread;
            }
    );

    public static ThreadPoolExecutor FLUSH_CURRENT_OFFSET_EXECUTOR = new ThreadPoolExecutor(
            1, 1, 30L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(10),
            r -> {
                Thread thread = new Thread(r);
                thread.setName("flush-current-offset-executor");
                return thread;
            }
    );

}
