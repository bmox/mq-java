package xyz.lp.mq.broker.model;

import lombok.Data;
import xyz.lp.mq.broker.utils.ByteConvertUtil;

@Data
public class CommitLogIndexModel {

    private int commitLogFilename;

    private int msgIndex;

    private int msgLen;

    public byte[] toBytes() {
        return ByteConvertUtil.intToBytes(commitLogFilename, msgIndex, msgLen);
    }

}
