package xyz.lp.mq.broker.config;

import org.apache.commons.lang3.StringUtils;
import xyz.lp.mq.broker.cache.CommonCache;

public class TopicInfoLoader {

    public void loadProperties() {
        GlobalProperties globalProperties = CommonCache.getGlobalProperties();
        String home = globalProperties.getMqHome();
        if (StringUtils.isBlank(home)) {
            throw new IllegalArgumentException("MQ_HOME is not set");
        }
        String topicInfoPath = home + "/broker/conf/topic-info.json";
        TopicInfo topicInfo = new TopicInfo();
    }

}
