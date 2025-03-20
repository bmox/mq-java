package xyz.lp.mq.broker.model;

import xyz.lp.mq.broker.utils.ByteConvertUtil;

import java.util.Arrays;

public class CommitLogMsgModel {

    private int size;

    private byte[] content;

    public byte[] toBytes() {
        byte[] sizeBytes = ByteConvertUtil.intToBytes(size);
        byte[] result = new byte[sizeBytes.length + content.length];
        System.arraycopy(sizeBytes, 0, result, 0, sizeBytes.length);
        System.arraycopy(content, 0, result, sizeBytes.length, content.length);
        return result;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommitLogMsgModel{" +
                "size=" + size +
                ", content=" + Arrays.toString(content) +
                '}';
    }

}
