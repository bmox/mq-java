package xyz.lp.mq.broker.core;

import xyz.lp.mq.broker.cache.CommonCache;
import xyz.lp.mq.broker.model.CommitLogMMapFileModel;
import xyz.lp.mq.broker.model.CommitLogModel;
import xyz.lp.mq.broker.model.CommitLogMsgModel;
import xyz.lp.mq.broker.model.MMapFileModel;

import java.io.IOException;
import java.util.Objects;

public class CommitLogAppendHandler {

    private CommitLogMMapFileModelManager commitLogMMapFileModelManager = new CommitLogMMapFileModelManager();

    public CommitLogAppendHandler() {}

    public void prepareMMapLoading(String topicName) throws IOException {
        CommitLogMMapFileModel mMapFileModel = new CommitLogMMapFileModel();
        CommitLogModel latestCommitLog = CommonCache.getLatestCommitLog(topicName);
        mMapFileModel.loadCommitLogFileInMMap(topicName, 0, latestCommitLog.getSize());
        commitLogMMapFileModelManager.put(topicName, mMapFileModel);
    }

    public void appendMsg(String topic, byte[] content) throws IOException {
        CommitLogMMapFileModel commitLogMMapFileModel = commitLogMMapFileModelManager.get(topic);
        if (Objects.isNull(commitLogMMapFileModel)) {
            throw new RuntimeException("topic is invalid");
        }
        CommitLogMsgModel commitLogMsg = new CommitLogMsgModel();
        commitLogMsg.setSize(content.length);
        commitLogMsg.setContent(content);
        commitLogMMapFileModel.writeContent(commitLogMsg);
    }

    public void readMsg(String topic) {
        MMapFileModel mMapFileModel = commitLogMMapFileModelManager.get(topic);
        if (Objects.isNull(mMapFileModel)) {
            throw new RuntimeException("topic is invalid");
        }
        byte[] content = mMapFileModel.readContent(0, 10);
        System.out.println(new String(content));
    }

}
