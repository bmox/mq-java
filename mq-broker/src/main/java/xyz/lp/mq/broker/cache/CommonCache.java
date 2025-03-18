package xyz.lp.mq.broker.cache;

import xyz.lp.mq.broker.config.GlobalProperties;
import xyz.lp.mq.broker.config.TopicInfo;

public class CommonCache {

    public static GlobalProperties globalProperties = new GlobalProperties();
    public static TopicInfo topicInfo = new TopicInfo();

    public static TopicInfo getTopicInfo() {
        return topicInfo;
    }

    public static void setTopicInfo(TopicInfo topicInfo) {
        CommonCache.topicInfo = topicInfo;
    }

    public static GlobalProperties getGlobalProperties() {
        return globalProperties;
    }

    public static void setGlobalProperties(GlobalProperties globalProperties) {
        CommonCache.globalProperties = globalProperties;
    }
}
