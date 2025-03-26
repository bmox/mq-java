package xyz.lp.mq.broker.model;

import xyz.lp.mq.broker.cache.CommonCache;
import xyz.lp.mq.broker.utils.CommitLogUtil;
import xyz.lp.mq.broker.utils.Lock;
import xyz.lp.mq.broker.utils.UnfairReentrantLock;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class CommitLogMMapFileModel extends MMapFileModel {

    private String topicName;
    private Lock putLock = new UnfairReentrantLock();

    public void loadCommitLogFileInMMap(String topicName, int pos, int size) throws IOException {
        this.topicName = topicName;
        String filePath = getLatestCommitLogFilePath(topicName);
        loadFileInMMap(filePath, pos, size);
    }

    private String getLatestCommitLogFilePath(String topicName) {
        TopicModel topicModel = CommonCache.getTopicModelMap().get(topicName);
        if (Objects.isNull(topicModel)) {
            throw new RuntimeException("topic not found: " + topicName);
        }
        String latestCommitLogFilePath;
        CommitLogModel latestCommitLog = topicModel.getLatestCommitLog();
        if (latestCommitLog.isFull()) {
            latestCommitLogFilePath = latestCommitLog.createNewCommitLogFile(topicName);
        } else {
            latestCommitLogFilePath = CommitLogUtil.buildCommitLogFilePath( topicName, latestCommitLog.getFilename());
        }
        return latestCommitLogFilePath;
    }

    public void writeContent(CommitLogMsgModel commitLogMsg) throws IOException {
        writeContent(commitLogMsg, false);
    }

    public void writeContent(CommitLogMsgModel commitLogMsg, boolean force) throws IOException {
        // 封装 raw data
        // 写满需要新建文件并 map
        // offset manager
        // - 线程安全
        //   - AtomicLong，顺序无法保证
        //   - 加锁
        // 定时刷盘

        byte[] bytes = commitLogMsg.toBytes();

        CommitLogModel latestCommitLog = CommonCache.getLatestCommitLog(topicName);

        try {
            putLock.lock();
            if (latestCommitLog.willFull((long) bytes.length)) {
                String latestCommitLogFilePath = latestCommitLog.createNewCommitLogFile(topicName);
                loadFileInMMap(latestCommitLogFilePath, 0, latestCommitLog.getSize());
            }
            writeContent(bytes, force);
            AtomicInteger latestOffset = latestCommitLog.getOffset();
            dispatch(latestOffset, bytes.length);
            latestOffset.getAndAdd(bytes.length);
        } finally {
            putLock.unlock();
        }

    }

    private void dispatch(AtomicInteger latestOffset, int length) throws IOException {
        CommitLogIndexModel commitLogIndexModel = new CommitLogIndexModel();
        commitLogIndexModel.setCommitLogFilename(CommonCache.getLatestCommitLog(topicName).getFilenameInt());
        commitLogIndexModel.setMsgIndex(latestOffset.get());
        commitLogIndexModel.setMsgLen(length);
        // TODO: queueId 使用某个算法计算而来
        int queueId = 0;
        QueueMMapFileModel queueMMapFileModel = CommonCache.getQueueMMapFileModel(topicName, queueId);
        byte[] bytes = commitLogIndexModel.toBytes();
        queueMMapFileModel.writeContent(bytes);
    }

}
