package xyz.lp.mq.broker.model;

import lombok.Data;
import xyz.lp.mq.broker.constants.BrokerConstants;
import xyz.lp.mq.broker.utils.QueueUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Data
public class QueueModel {

    private Integer id;

    private String filename;

    private Integer size;

    private Integer lastOffset;

    private Integer latestOffset;

    public boolean isFull() {
        return latestOffset >= size;
    }

    public String createNewQueueFile(String topicName) {
        String newQueueFileName = QueueUtil.buildNextQueueFileName(this.getFilename());
        String filePath = QueueUtil.buildQueueFilePath(topicName, this.id, newQueueFileName);
        try {
            Files.createFile(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        filename = newQueueFileName;
        lastOffset = 0;
        latestOffset = 0;
        size = BrokerConstants.QUEUE_SIZE;
        return filePath;
    }

}
