package xyz.lp.mq.broker.model;

public class QueueCurrentOffsetModel {
    private Integer id;
    private String filename;
    private Integer offset;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "QueueCurrentOffsetModel{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                ", offset=" + offset +
                '}';
    }

}
