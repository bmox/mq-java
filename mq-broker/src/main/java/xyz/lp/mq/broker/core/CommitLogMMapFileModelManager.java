package xyz.lp.mq.broker.core;

import xyz.lp.mq.broker.model.CommitLogMMapFileModel;

import java.util.HashMap;
import java.util.Map;

public class CommitLogMMapFileModelManager {

    // <key: topic name, value: mmap>
    private static Map<String, CommitLogMMapFileModel> commitLogMMapFileModelMap = new HashMap<>();

    public void put(String topic, CommitLogMMapFileModel commitLogMMapFileModel) {
        commitLogMMapFileModelMap.put(topic, commitLogMMapFileModel);
    }

    public CommitLogMMapFileModel get(String topic) {
        return commitLogMMapFileModelMap.get(topic);
    }

}
