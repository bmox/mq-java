package xyz.lp.mq.broker.model;

public class QueueModel {

    private Integer id;

    private String fileName;

    private Integer offsetLimit;

    private Integer lastOffset;

    private Integer latestOffset;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getOffsetLimit() {
        return offsetLimit;
    }

    public void setOffsetLimit(Integer offsetLimit) {
        this.offsetLimit = offsetLimit;
    }

    public Integer getLastOffset() {
        return lastOffset;
    }

    public void setLastOffset(Integer lastOffset) {
        this.lastOffset = lastOffset;
    }

    public Integer getLatestOffset() {
        return latestOffset;
    }

    public void setLatestOffset(Integer latestOffset) {
        this.latestOffset = latestOffset;
    }

    @Override
    public String toString() {
        return "QueueModel{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", offsetLimit=" + offsetLimit +
                ", lastOffset=" + lastOffset +
                ", latestOffset=" + latestOffset +
                '}';
    }
}
