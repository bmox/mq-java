package xyz.lp.mq.broker.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import xyz.lp.mq.broker.cache.CommonCache;
import xyz.lp.mq.broker.constants.BrokerConstants;
import xyz.lp.mq.broker.model.CurrentOffsetModel;
import xyz.lp.mq.broker.utils.FileUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class CurrentOffsetLoader {

    private String currentOffsetFilePath;

    public void loadProperties() {
        GlobalProperties globalProperties = CommonCache.getGlobalProperties();
        String home = globalProperties.getMqHome();
        if (StringUtils.isBlank(home)) {
            throw new IllegalArgumentException("MQ_HOME is not set");
        }
        currentOffsetFilePath = home + BrokerConstants.CURRENT_OFFSET_FILE_PATH;
        String currentOffsetStr = FileUtil.readFromFile(currentOffsetFilePath);
        ObjectMapper objectMapper = new ObjectMapper();
        List<CurrentOffsetModel> currentOffsetModels;
        try {
            currentOffsetModels = objectMapper.readValue(currentOffsetStr, new TypeReference<List<CurrentOffsetModel>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        CommonCache.setCurrentOffsetModels(currentOffsetModels);
    }

    // 开启定时任务将 topic 写入磁盘
    public void startFlushCurrentOffsetTask() {
        CommonThreadPoolConfig.FLUSH_CURRENT_OFFSET_EXECUTOR.execute(() -> {
            do {
                try {
                    TimeUnit.SECONDS.sleep(3);
                    List<CurrentOffsetModel> currentOffsetModels = CommonCache.getCurrentOffsetModels();
                    ObjectMapper objectMapper = new ObjectMapper();
                    String currentOffsetStr = objectMapper.writeValueAsString(currentOffsetModels);
                    FileUtil.overwriteFile(currentOffsetFilePath, currentOffsetStr);
                } catch (InterruptedException | JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            } while (true);
        });
    }

}
