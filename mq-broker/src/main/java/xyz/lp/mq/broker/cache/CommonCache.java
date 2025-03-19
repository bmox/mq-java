package xyz.lp.mq.broker.cache;

import xyz.lp.mq.broker.config.GlobalProperties;
import xyz.lp.mq.broker.model.TopicModel;

import java.util.ArrayList;
import java.util.List;

public class CommonCache {

    public static GlobalProperties globalProperties = new GlobalProperties();
    public static List<TopicModel> topicModelList = new ArrayList<>();

    public static List<TopicModel> getTopicModelList() {
        return topicModelList;
    }

    public static void setTopicModelList(List<TopicModel> topicModelList) {
        CommonCache.topicModelList = topicModelList;
    }

    public static GlobalProperties getGlobalProperties() {
        return globalProperties;
    }

    public static void setGlobalProperties(GlobalProperties globalProperties) {
        CommonCache.globalProperties = globalProperties;
    }
}
