package xyz.lp.mq.broker;

import xyz.lp.mq.broker.cache.CommonCache;
import xyz.lp.mq.broker.config.GlobalPropertiesLoader;
import xyz.lp.mq.broker.config.TopicLoader;
import xyz.lp.mq.broker.core.CommitLogAppendHandler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BrokerStartup {

    private static GlobalPropertiesLoader globalPropertiesLoader;
    private static TopicLoader topicLoader;
    private static CommitLogAppendHandler commitLogAppendHandler;

    private static void initProperties() throws IOException {
        globalPropertiesLoader = new GlobalPropertiesLoader();
        globalPropertiesLoader.loadProperties();

        topicLoader = new TopicLoader();
        topicLoader.loadProperties();

        commitLogAppendHandler = new CommitLogAppendHandler();
        for (String topicName : CommonCache.getTopicModelMap().keySet()) {
            commitLogAppendHandler.prepareMMapLoading(topicName);
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        initProperties();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 256; i++) {
            sb.append('A');
        }
        String msg = sb.toString();
        for (int i = 0; i < 10; i++) {
            commitLogAppendHandler.appendMsg("order", msg.getBytes());
            TimeUnit.SECONDS.sleep(2);
        }
    }

}
