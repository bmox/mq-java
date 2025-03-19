package xyz.lp.mq.broker.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import xyz.lp.mq.broker.cache.CommonCache;
import xyz.lp.mq.broker.constants.BrokerConstants;
import xyz.lp.mq.broker.model.TopicModel;
import xyz.lp.mq.broker.utils.FileContentReaderUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TopicLoader {

    public void loadProperties() {
        GlobalProperties globalProperties = CommonCache.getGlobalProperties();
        String home = globalProperties.getMqHome();
        if (StringUtils.isBlank(home)) {
            throw new IllegalArgumentException("MQ_HOME is not set");
        }
        String topicModelPath = home + BrokerConstants.TOPIC_INFO_FILE_PATH;
        String topicModelStr = FileContentReaderUtil.readFromFile(topicModelPath);
        ObjectMapper objectMapper = new ObjectMapper();
        List<TopicModel> topicModels;
        try {
            topicModels = objectMapper.readValue(topicModelStr, new TypeReference<List<TopicModel>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Map<String, TopicModel> topicModelMap = topicModels.stream()
                .collect(Collectors.toMap(TopicModel::getTopicName, topicModel -> topicModel));
        CommonCache.setTopicModelMap(topicModelMap);
    }

}
