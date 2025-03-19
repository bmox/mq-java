package xyz.lp.mq.broker;

import xyz.lp.mq.broker.cache.CommonCache;
import xyz.lp.mq.broker.config.GlobalPropertiesLoader;
import xyz.lp.mq.broker.config.TopicLoader;
import xyz.lp.mq.broker.constants.BrokerConstants;
import xyz.lp.mq.broker.core.MessageAppendHandler;
import xyz.lp.mq.broker.model.TopicModel;

import java.io.IOException;

public class BrokerStartup {

    private static GlobalPropertiesLoader globalPropertiesLoader;
    private static TopicLoader topicLoader;
    private static MessageAppendHandler messageAppendHandler;

    private static void initProperties() throws IOException {
        globalPropertiesLoader = new GlobalPropertiesLoader();
        globalPropertiesLoader.loadProperties();

        topicLoader = new TopicLoader();
        topicLoader.loadProperties();

        messageAppendHandler = new MessageAppendHandler();
        String mqHome = CommonCache.getGlobalProperties().getMqHome();
        for (TopicModel topicModel : CommonCache.getTopicModelList()) {
            messageAppendHandler.prepareMMapLoading(mqHome + BrokerConstants.TOPIC_DIR_PATH + "/" + topicModel.getTopicName() + "/000", topicModel.getTopicName());
        }

    }

    public static void main(String[] args) throws IOException {
        initProperties();
        messageAppendHandler.appendMsg("order", "hello startup");
        messageAppendHandler.readMsg("order");
    }

}
