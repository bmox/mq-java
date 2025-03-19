package xyz.lp.mq.broker.cache;

import xyz.lp.mq.broker.config.GlobalProperties;
import xyz.lp.mq.broker.model.TopicModel;

import java.util.HashMap;
import java.util.Map;

public class CommonCache {

    public static GlobalProperties globalProperties = new GlobalProperties();
    public static Map<String, TopicModel> topicModelMap = new HashMap<>();

    public static GlobalProperties getGlobalProperties() {
        return globalProperties;
    }

    public static void setGlobalProperties(GlobalProperties globalProperties) {
        CommonCache.globalProperties = globalProperties;
    }

    public static Map<String, TopicModel> getTopicModelMap() {
        return topicModelMap;
    }

    public static void setTopicModelMap(Map<String, TopicModel> topicModelMap) {
        CommonCache.topicModelMap = topicModelMap;
    }

}
