package xyz.lp.mq.broker.model;

public class QueueItemModel {

    private String commitLogFilename;

    private int msgIndex;

    private int msgLen;

    public String getCommitLogFilename() {
        return commitLogFilename;
    }

    public void setCommitLogFilename(String commitLogFilename) {
        this.commitLogFilename = commitLogFilename;
    }

    public int getMsgIndex() {
        return msgIndex;
    }

    public void setMsgIndex(int msgIndex) {
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
        return "QueueItemModel{" +
                "commitLogFilename='" + commitLogFilename + '\'' +
                ", msgIndex=" + msgIndex +
                ", msgLen=" + msgLen +
                '}';
    }

}
