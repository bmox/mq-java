package xyz.lp.mq.broker.cache;

import xyz.lp.mq.broker.config.GlobalProperties;
import xyz.lp.mq.broker.core.QueueMMapFileModelManager;
import xyz.lp.mq.broker.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class CommonCache {

    public static GlobalProperties globalProperties = new GlobalProperties();
    public static List<TopicModel> topicModels = new ArrayList<>();
    public static List<CurrentOffsetModel> currentOffsetModels = new ArrayList<>();
    public static QueueMMapFileModelManager queueMMapFileModelManager;

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

    public static TopicModel getTopicModel(String topicName) {
        return getTopicModelMap().get(topicName);
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

    public static QueueMMapFileModel getQueueMMapFileModel(String topicName, int queueId) {
        return null;
    }

    public static void setQueueMMapFileModelManager(QueueMMapFileModelManager queueMMapFileModelManager) {
        CommonCache.queueMMapFileModelManager = queueMMapFileModelManager;
    }

    public static QueueMMapFileModelManager getQueueMMapFileModelManager() {
        return queueMMapFileModelManager;
    }

    public static QueueMMapFileModel getQueueMMapFileModel(String topicName, Integer queueId) {
        return queueMMapFileModelManager.get(topicName, queueId);
    }

    public static QueueModel getQueue(String topicName, Integer queueId) {
        TopicModel topicModel = getTopicModelMap().get(topicName);
        if (Objects.isNull(topicModel)) {
            throw new RuntimeException("topic not found: " + topicName);
        }
        return topicModel.getQueue(queueId);
    }

}
