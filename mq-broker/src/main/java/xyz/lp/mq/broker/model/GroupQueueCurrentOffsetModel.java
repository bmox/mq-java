package xyz.lp.mq.broker.model;

import java.util.List;

public class GroupQueueCurrentOffsetModel {

    private String groupName;

    private List<QueueCurrentOffsetModel> queueList;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<QueueCurrentOffsetModel> getQueueList() {
        return queueList;
    }

    public void setQueueList(List<QueueCurrentOffsetModel> queueList) {
        this.queueList = queueList;
    }

    @Override
    public String toString() {
        return "GroupQueueCurrentOffsetModel{" +
                "groupName='" + groupName + '\'' +
                ", queueList=" + queueList +
                '}';
    }
}
