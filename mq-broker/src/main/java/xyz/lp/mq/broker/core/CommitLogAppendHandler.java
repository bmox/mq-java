package xyz.lp.mq.broker.core;

import xyz.lp.mq.broker.model.CommitLogMsgModel;

import java.io.IOException;
import java.util.Objects;

public class CommitLogAppendHandler {

    private MMapFileModelManager mMapFileModelManager = new MMapFileModelManager();

    public CommitLogAppendHandler() {}

    public void prepareMMapLoading(String topicName) throws IOException {
        MMapFileModel mMapFileModel = new MMapFileModel();
        mMapFileModel.loadFileInMMap(topicName, 0, 1024 * 1024);
        mMapFileModelManager.put(topicName, mMapFileModel);
    }

    public void appendMsg(String topic, byte[] content) {
        MMapFileModel mMapFileModel = mMapFileModelManager.get(topic);
        if (Objects.isNull(mMapFileModel)) {
            throw new RuntimeException("topic is invalid");
        }
        CommitLogMsgModel commitLogMsg = new CommitLogMsgModel();
        commitLogMsg.setSize(content.length);
        commitLogMsg.setContent(content);
        mMapFileModel.writeContent(commitLogMsg);
    }

    public void readMsg(String topic) {
        MMapFileModel mMapFileModel = mMapFileModelManager.get(topic);
        if (Objects.isNull(mMapFileModel)) {
            throw new RuntimeException("topic is invalid");
        }
        byte[] content = mMapFileModel.readContent(0, 10);
        System.out.println(new String(content));
    }

}
