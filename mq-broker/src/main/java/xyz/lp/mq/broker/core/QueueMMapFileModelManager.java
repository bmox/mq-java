package xyz.lp.mq.broker.core;

import xyz.lp.mq.broker.model.QueueMMapFileModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class QueueMMapFileModelManager {

    // <key: topic name, value: mmap>
    private static Map<String, Map<Integer, QueueMMapFileModel>> queueMMapFileModelMap = new HashMap<>();

    public void put(String topic, Integer queueId, QueueMMapFileModel queueMMapFileModel) {
        Map<Integer, QueueMMapFileModel> queueMMapFileModelMap
                = QueueMMapFileModelManager.queueMMapFileModelMap.computeIfAbsent(topic, k -> new HashMap<>());
        queueMMapFileModelMap.put(queueId, queueMMapFileModel);
    }

    public QueueMMapFileModel get(String topic, Integer queueId) {
        Map<Integer, QueueMMapFileModel> queueMMapFileModelMap
                = QueueMMapFileModelManager.queueMMapFileModelMap.get(topic);
        if (Objects.isNull(queueMMapFileModelMap)) {
            throw new RuntimeException("topic not found: " + topic);
        }
        return queueMMapFileModelMap.get(queueId);
    }

}
