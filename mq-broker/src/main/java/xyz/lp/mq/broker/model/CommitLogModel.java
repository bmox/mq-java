package xyz.lp.mq.broker.model;

public class CommitLogModel {

    private String fileName;

    private Long offset;

    private Long size;

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
