package xyz.lp.mq.broker.model;

import java.util.List;

public class CurrentOffsetModel {

    private String topicName;
    private List<GroupQueueCurrentOffsetModel> groupList;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public List<GroupQueueCurrentOffsetModel> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<GroupQueueCurrentOffsetModel> groupList) {
        this.groupList = groupList;
    }

    @Override
    public String toString() {
        return "CurrentOffsetModel{" +
                "topicName='" + topicName + '\'' +
                ", groupList=" + groupList +
                '}';
    }
}
