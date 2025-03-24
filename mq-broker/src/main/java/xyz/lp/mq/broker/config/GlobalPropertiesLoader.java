package xyz.lp.mq.broker.config;

import org.apache.commons.lang3.StringUtils;
import xyz.lp.mq.broker.cache.CommonCache;
import xyz.lp.mq.broker.constants.BrokerConstants;


public class GlobalPropertiesLoader {

    public void loadProperties() {
        GlobalProperties globalProperties = new GlobalProperties();
        String mqHome = System.getenv(BrokerConstants.MQ_HOME);
        if (StringUtils.isBlank(mqHome)) {
            throw new IllegalArgumentException("MQ_HOME is not set");
        }
        globalProperties.setMqHome(mqHome);
        CommonCache.setGlobalProperties(globalProperties);
    }

}
