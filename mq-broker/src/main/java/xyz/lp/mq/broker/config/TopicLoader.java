package xyz.lp.mq.broker.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import xyz.lp.mq.broker.cache.CommonCache;
import xyz.lp.mq.broker.constants.BrokerConstants;
import xyz.lp.mq.broker.model.TopicModel;
import xyz.lp.mq.broker.utils.FileUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TopicLoader {

    private String topicFilePath;

    public void loadProperties() {
        GlobalProperties globalProperties = CommonCache.getGlobalProperties();
        String home = globalProperties.getMqHome();
        if (StringUtils.isBlank(home)) {
            throw new IllegalArgumentException("MQ_HOME is not set");
        }
        topicFilePath = home + BrokerConstants.TOPIC_INFO_FILE_PATH;
        String topicModelStr = FileUtil.readFromFile(topicFilePath);
        ObjectMapper objectMapper = new ObjectMapper();
        List<TopicModel> topicModels;
        try {
            topicModels = objectMapper.readValue(topicModelStr, new TypeReference<List<TopicModel>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        CommonCache.setTopicModels(topicModels);
        startFlushTopicTask();
    }

    // 开启定时任务将 topic 写入磁盘
    public void startFlushTopicTask() {
        CommonThreadPoolConfig.FLUSH_TOPIC_EXECUTOR.execute(() -> {
            do {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    List<TopicModel> topicModels = CommonCache.getTopicModels();
                    ObjectMapper objectMapper = new ObjectMapper();
                    String topicModelStr = objectMapper.writeValueAsString(topicModels);
                    FileUtil.overwriteFile(topicFilePath, topicModelStr);
                } catch (InterruptedException | JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            } while (true);
        });
    }

}
