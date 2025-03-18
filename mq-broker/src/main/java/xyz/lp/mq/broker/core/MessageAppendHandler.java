package xyz.lp.mq.broker.core;

import java.io.IOException;
import java.util.Objects;

public class MessageAppendHandler {

    private MMapFileModelManager mMapFileModelManager = new MMapFileModelManager();

    public static final String filePath = "/Users/ruifeng/bmox/mq-java/broker/store/topic/order/000";
    public static final String topicName = "order";

    public MessageAppendHandler() throws IOException {
        prepareMMapLoading();
    }

    private void prepareMMapLoading() throws IOException {
        MMapFileModel mMapFileModel = new MMapFileModel();
        mMapFileModel.loadFileInMMap(filePath, 0, 1024 * 1024);
        mMapFileModelManager.put(topicName, mMapFileModel);
    }

    public void appendMsg(String topic, String content) {
        MMapFileModel mMapFileModel = mMapFileModelManager.get(topic);
        if (Objects.isNull(mMapFileModel)) {
            throw new RuntimeException("topic is invalid");
        }
        mMapFileModel.writeContent(content.getBytes());
    }

    private void readMsg(String topic) {
        MMapFileModel mMapFileModel = mMapFileModelManager.get(topic);
        if (Objects.isNull(mMapFileModel)) {
            throw new RuntimeException("topic is invalid");
        }
        byte[] content = mMapFileModel.readContent(0, 10);
        System.out.println(new String(content));
    }

    public static void main(String[] args) throws IOException {
        MessageAppendHandler messageAppendHandler = new MessageAppendHandler();
        messageAppendHandler.appendMsg(topicName, "hello order");
        messageAppendHandler.readMsg(topicName);
    }

}
