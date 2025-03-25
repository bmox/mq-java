package xyz.lp.mq.broker.model;

import xyz.lp.mq.broker.cache.CommonCache;
import xyz.lp.mq.broker.utils.Lock;
import xyz.lp.mq.broker.utils.QueueUtil;
import xyz.lp.mq.broker.utils.UnfairReentrantLock;

import java.io.IOException;
import java.util.Objects;

public class QueueMMapFileModel extends MMapFileModel {

    private String topicName;
    private Integer queueId;
    private Lock putLock = new UnfairReentrantLock();

    public void loadQueueFileInMMap(String topicName, Integer queueId, int pos, int size) throws IOException {
        this.topicName = topicName;
        this.queueId = queueId;
        String filePath = getLatestFilePath(topicName, queueId);
        loadFileInMMap(filePath, pos, size);
    }

    private String getLatestFilePath(String topicName, Integer queueId) {
        TopicModel topicModel = CommonCache.getTopicModelMap().get(topicName);
        if (Objects.isNull(topicModel)) {
            throw new RuntimeException("topic not found: " + topicName);
        }
        String latestQueueFilePath;
        QueueModel queue = topicModel.getQueue(queueId);
        if (queue.isFull()) {
            latestQueueFilePath = queue.createNewQueueFile(topicName);
        } else {
            latestQueueFilePath = QueueUtil.buildQueueFilePath(topicName, queueId, queue.getFilename());
        }
        return latestQueueFilePath;
    }

}
