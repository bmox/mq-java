package xyz.lp.mq.broker.model;

import java.util.List;

public class TopicModel {

    private String topicName;

    private CommitLogModel latestCommitLog;

    private List<QueueModel> queueList;

    private Long ctime;

    private Long mtime;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public CommitLogModel getLatestCommitLog() {
        return latestCommitLog;
    }

    public void setLatestCommitLog(CommitLogModel latestCommitLog) {
        this.latestCommitLog = latestCommitLog;
    }

    public List<QueueModel> getQueueList() {
        return queueList;
    }

    public void setQueueList(List<QueueModel> queueList) {
        this.queueList = queueList;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public Long getMtime() {
        return mtime;
    }

    public void setMtime(Long mtime) {
        this.mtime = mtime;
    }

    @Override
    public String toString() {
        return "TopicModel{" +
                "topicName='" + topicName + '\'' +
                ", latestCommitLog=" + latestCommitLog +
                ", queueList=" + queueList +
                ", ctime=" + ctime +
                ", mtime=" + mtime +
                '}';
    }
}
