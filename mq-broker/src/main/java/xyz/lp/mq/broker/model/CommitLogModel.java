package xyz.lp.mq.broker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import xyz.lp.mq.broker.constants.BrokerConstants;
import xyz.lp.mq.broker.utils.CommitLogUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitLogModel {

    private String filename;

    private AtomicInteger offset;

    private Integer size;

    public String createNewCommitLogFile(String topicName) {
        String newCommitLogFileName = CommitLogUtil.buildNextCommitLogFileName(this.getFilename());
        String filePath = CommitLogUtil.buildCommitLogFilePath(topicName, newCommitLogFileName);
        try {
            Files.createFile(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        filename = newCommitLogFileName;
        offset.set(0);
        size = BrokerConstants.COMMIT_LOG_SIZE;
        return filePath;
    }

    public boolean willFull(Long size) {
        return this.offset.get() + size > this.size;
    }

    public boolean isFull() {
        return size.equals(offset.get());
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public AtomicInteger getOffset() {
        return offset;
    }

    public void setOffset(AtomicInteger offset) {
        this.offset = offset;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "CommitLogModel{" +
                "fileName='" + filename + '\'' +
                ", offset=" + offset +
                ", size=" + size +
                '}';
    }
}
