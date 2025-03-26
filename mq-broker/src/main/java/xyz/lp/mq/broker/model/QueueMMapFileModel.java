package xyz.lp.mq.broker.model;

import xyz.lp.mq.broker.cache.CommonCache;
import xyz.lp.mq.broker.constants.BrokerConstants;
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
        if (Objects.isNull(queue)) {
            throw new RuntimeException("queue not found: " + queueId);
        }
        if (queue.isFull()) {
            latestQueueFilePath = queue.createNewQueueFile(topicName);
        } else {
            latestQueueFilePath = QueueUtil.buildQueueFilePath(topicName, queueId, queue.getFilename());
        }
        return latestQueueFilePath;
    }

    public void writeContent(byte[] bytes) throws IOException {
        writeContent(bytes, false);
    }

    public void writeContent(byte[] bytes, boolean force) throws IOException {
        QueueModel queue = CommonCache.getQueue(topicName, queueId);
        try {
            putLock.lock();

            if (queue.willFull(bytes.length)) {
                String latestQueueFilePath = queue.createNewQueueFile(topicName);
                loadFileInMMap(latestQueueFilePath, 0, BrokerConstants.QUEUE_SIZE);
            }
            super.writeContent(bytes, force);
            queue.getLatestOffset().getAndAdd(bytes.length);
        } finally {
            putLock.unlock();
        }
    }

}
