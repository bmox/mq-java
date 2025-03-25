package xyz.lp.mq.broker.core;

import xyz.lp.mq.broker.cache.CommonCache;
import xyz.lp.mq.broker.model.QueueMMapFileModel;
import xyz.lp.mq.broker.model.QueueModel;
import xyz.lp.mq.broker.utils.QueueUtil;

import java.io.IOException;
import java.util.List;

public class QueueAppendHandler {

    private QueueMMapFileModelManager queueMMapFileModelManager = new QueueMMapFileModelManager();

    public QueueAppendHandler() {}

    public void prepareMMapLoading(String topicName) throws IOException {
        List<QueueModel> queueList = CommonCache.getQueueList(topicName);
        for (QueueModel queueModel : queueList) {
            QueueMMapFileModel queueMMapFileModel = new QueueMMapFileModel();
            String queueFilePath = QueueUtil.buildQueueFilePath(topicName, queueModel.getId(), queueModel.getFilename());
            queueMMapFileModel.loadFileInMMap(queueFilePath, queueModel.getLastOffset(), queueModel.getSize());
            queueMMapFileModelManager.put(topicName, queueModel.getId(), queueMMapFileModel);
        }
    }

    // public void appendMsg(String topic, byte[] content) throws IOException {
    //     CommitLogMMapFileModel commitLogMMapFileModel = commitLogMMapFileModelManager.get(topic);
    //     if (Objects.isNull(commitLogMMapFileModel)) {
    //         throw new RuntimeException("topic is invalid");
    //     }
    //     CommitLogMsgModel commitLogMsg = new CommitLogMsgModel();
    //     commitLogMsg.setSize(content.length);
    //     commitLogMsg.setContent(content);
    //     commitLogMMapFileModel.writeContent(commitLogMsg);
    // }
    //
    // public void readMsg(String topic) {
    //     MMapFileModel mMapFileModel = commitLogMMapFileModelManager.get(topic);
    //     if (Objects.isNull(mMapFileModel)) {
    //         throw new RuntimeException("topic is invalid");
    //     }
    //     byte[] content = mMapFileModel.readContent(0, 10);
    //     System.out.println(new String(content));
    // }

}
