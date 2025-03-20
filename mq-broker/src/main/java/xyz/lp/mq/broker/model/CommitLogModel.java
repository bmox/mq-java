package xyz.lp.mq.broker.model;

import xyz.lp.mq.broker.constants.BrokerConstants;
import xyz.lp.mq.broker.utils.CommitLogUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CommitLogModel {

    private String fileName;

    private Long offset;

    private Long size;

    public String createNewCommitLogFile(String topicName) {
        String newCommitLogFileName = CommitLogUtil.buildNextCommitLogFileName(this.getFileName());
        String filePath = CommitLogUtil.buildCommitLogFilePath(BrokerConstants.MQ_HOME, topicName, newCommitLogFileName);
        try {
            Files.createFile(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileName = newCommitLogFileName;
        return filePath;
    }

    public boolean willFull(Long size) {
        return this.offset + size > this.size;
    }

    public boolean isFull() {
        return offset.equals(size);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "CommitLogModel{" +
                "fileName='" + fileName + '\'' +
                ", offset=" + offset +
                ", size=" + size +
                '}';
    }
}
