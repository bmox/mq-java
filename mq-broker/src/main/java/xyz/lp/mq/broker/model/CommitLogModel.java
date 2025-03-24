package xyz.lp.mq.broker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import xyz.lp.mq.broker.constants.BrokerConstants;
import xyz.lp.mq.broker.utils.CommitLogUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicLong;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitLogModel {

    private String fileName;

    private AtomicLong offset;

    private Long size;

    public String createNewCommitLogFile(String topicName) {
        String newCommitLogFileName = CommitLogUtil.buildNextCommitLogFileName(this.getFileName());
        String filePath = CommitLogUtil.buildCommitLogFilePath(topicName, newCommitLogFileName);
        try {
            Files.createFile(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileName = newCommitLogFileName;
        offset.set(0L);
        size = Long.valueOf(BrokerConstants.COMMIT_LOG_SIZE);
        return filePath;
    }

    public boolean willFull(Long size) {
        return this.offset.get() + size > this.size;
    }

    public boolean isFull() {
        return size.equals(offset.get());
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public AtomicLong getOffset() {
        return offset;
    }

    public void setOffset(AtomicLong offset) {
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
