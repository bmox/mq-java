package xyz.lp.mq.broker.cache;

import xyz.lp.mq.broker.config.GlobalProperties;
import xyz.lp.mq.broker.model.CommitLogModel;
import xyz.lp.mq.broker.model.CurrentOffsetModel;
import xyz.lp.mq.broker.model.QueueModel;
import xyz.lp.mq.broker.model.TopicModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class CommonCache {

    public static GlobalProperties globalProperties = new GlobalProperties();
    public static List<TopicModel> topicModels = new ArrayList<>();
    public static List<CurrentOffsetModel> currentOffsetModels = new ArrayList<>();

    public static List<QueueModel> getQueueList(String topicName) {
        TopicModel topicModel = getTopicModelMap().get(topicName);
        if (Objects.isNull(topicModel)) {
            throw new RuntimeException("topic not found: " + topicName);
        }
        return topicModel.getQueueList();
    }

    public static GlobalProperties getGlobalProperties() {
        return globalProperties;
    }

    public static void setGlobalProperties(GlobalProperties globalProperties) {
        CommonCache.globalProperties = globalProperties;
    }

    public static Map<String, TopicModel> getTopicModelMap() {
        return topicModels.stream()
                .collect(Collectors.toMap(TopicModel::getTopicName, topicModel -> topicModel));
    }

    public static List<TopicModel> getTopicModels() {
        return topicModels;
    }

    public static void setTopicModels(List<TopicModel> topicModels) {
        CommonCache.topicModels = topicModels;
    }

    public static List<CurrentOffsetModel> getCurrentOffsetModels() {
        return currentOffsetModels;
    }

    public static void setCurrentOffsetModels(List<CurrentOffsetModel> currentOffsetModels) {
        CommonCache.currentOffsetModels = currentOffsetModels;
    }

    public static CommitLogModel getLatestCommitLog(String topicName) {
        TopicModel topicModel = getTopicModelMap().get(topicName);
        if (Objects.isNull(topicModel)) {
            throw new RuntimeException("topic not found: " + topicName);
        }
        return topicModel.getLatestCommitLog();
    }

}
