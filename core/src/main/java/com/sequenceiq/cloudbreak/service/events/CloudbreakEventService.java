package com.sequenceiq.cloudbreak.service.events;

import java.util.List;

import com.sequenceiq.cloudbreak.api.model.DetailedStackStatus;
import com.sequenceiq.cloudbreak.domain.CloudbreakEvent;

public interface CloudbreakEventService {
    void fireCloudbreakEvent(Long stackId, String eventType, String eventMessage);

    void fireCloudbreakStackEvent(Long stackId, String eventType, String eventMessage, DetailedStackStatus detailedStackStatus);

    void fireCloudbreakInstanceGroupEvent(Long stackId, String eventType, String eventMessage, String instanceGroupName);

    CloudbreakEvent createStackEvent(CloudbreakEventData eventData);

    List<CloudbreakEvent> cloudbreakEvents(String user, Long since);
}
