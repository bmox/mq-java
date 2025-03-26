package xyz.lp.mq.broker.utils;

import xyz.lp.mq.broker.cache.CommonCache;
import xyz.lp.mq.broker.constants.BrokerConstants;

public class QueueUtil {

    public static String buildFirstQueueFileName() {
        return "00000000";
    }

    public static String buildNextQueueFileName(String fileName) {
        if (fileName.length() != 8) {
            throw new IllegalArgumentException(
                    "invalid queue file name: " + fileName + ", length: " + fileName.length() + ", should be 8");
        }
        long l = Long.parseLong(fileName, 10) + 1;
        if (l > 99999999) {
            throw new IllegalArgumentException("queue file name overflow: " + fileName);
        }
        return String.format("%08d",  l);
    }

    public static String buildQueueFilePath(String topicName, Integer queueId, String fileName) {
        return CommonCache.getGlobalProperties().getMqHome() + BrokerConstants.COMMIT_LOG_TOPIC_DIR_PATH + "/" +
                topicName + "/" + queueId + "/" + fileName;
    }

}
