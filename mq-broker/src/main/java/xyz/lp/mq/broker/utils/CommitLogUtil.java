package xyz.lp.mq.broker.utils;

import xyz.lp.mq.broker.cache.CommonCache;
import xyz.lp.mq.broker.constants.BrokerConstants;

public class CommitLogUtil {

    public static String buildFirstCommitLogFileName() {
        return "00000000";
    }

    public static String buildNextCommitLogFileName(String fileName) {
        if (fileName.length() != 8) {
            throw new IllegalArgumentException(
                    "invalid commit log file name: " + fileName + ", length: " + fileName.length() + ", should be 8");
        }
        long l = Long.parseLong(fileName, 10) + 1;
        if (l > 99999999) {
            throw new IllegalArgumentException("commit log file name overflow: " + fileName);
        }
        return String.format("%08d",  l);
    }

    public static String buildCommitLogFilePath(String topicName, String fileName) {
        return CommonCache.getGlobalProperties().getMqHome() + BrokerConstants.TOPIC_DIR_PATH + "/" + topicName +
                "/" + fileName;
    }

}
