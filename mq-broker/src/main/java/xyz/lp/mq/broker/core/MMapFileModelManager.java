package xyz.lp.mq.broker.core;

import java.util.HashMap;
import java.util.Map;

public class MMapFileModelManager {

    // <key: topic name, value: mmap>
    private static Map<String, MMapFileModel> mMapFileModelMap = new HashMap<>();

    public void put(String topic, MMapFileModel mMapFileModel) {
        mMapFileModelMap.put(topic, mMapFileModel);
    }

    public MMapFileModel get(String topic) {
        return mMapFileModelMap.get(topic);
    }

}
