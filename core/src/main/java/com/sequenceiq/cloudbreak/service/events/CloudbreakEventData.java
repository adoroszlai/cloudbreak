package com.sequenceiq.cloudbreak.service.events;

import com.sequenceiq.cloudbreak.api.model.DetailedStackStatus;

public class CloudbreakEventData {
    private Long entityId;

    private String eventType;

    private String eventMessage;

    private String statusType;

    private DetailedStackStatus detailedStatus;

    public CloudbreakEventData(Long entityId, String eventType, String eventMessage, String statusType, DetailedStackStatus detailedStatus) {
        this.entityId = entityId;
        this.eventType = eventType;
        this.eventMessage = eventMessage;
        this.statusType = statusType;
        this.detailedStatus = detailedStatus;
    }

    public CloudbreakEventData(Long entityId, String eventType, String eventMessage) {
        this(entityId, eventType, eventMessage, null, null);
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventMessage() {
        return eventMessage;
    }

    public void setEventMessage(String eventMessage) {
        this.eventMessage = eventMessage;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public DetailedStackStatus getDetailedStatus() {
        return detailedStatus;
    }

    public void setDetailedStatus(DetailedStackStatus detailedStatus) {
        this.detailedStatus = detailedStatus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CloudbreakEventData{");
        sb.append("entityId=").append(entityId);
        sb.append(", eventType='").append(eventType).append('\'');
        sb.append(", eventMessage='").append(eventMessage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
