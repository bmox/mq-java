package xyz.lp.mq.broker.model;

public class ConsumerQueueDetailModel {

    private String commitLogFilename;

    private long msgIndex;

    private int msgLen;

    public String getCommitLogFilename() {
        return commitLogFilename;
    }

    public void setCommitLogFilename(String commitLogFilename) {
        this.commitLogFilename = commitLogFilename;
    }

    public long getMsgIndex() {
        return msgIndex;
    }

    public void setMsgIndex(long msgIndex) {
        this.msgIndex = msgIndex;
    }

    public int getMsgLen() {
        return msgLen;
    }

    public void setMsgLen(int msgLen) {
        this.msgLen = msgLen;
    }

    @Override
    public String toString() {
        return "ConsumerQueueDetailModel{" +
                "commitLogFilename='" + commitLogFilename + '\'' +
                ", msgIndex=" + msgIndex +
                ", msgLen=" + msgLen +
                '}';
    }
}
