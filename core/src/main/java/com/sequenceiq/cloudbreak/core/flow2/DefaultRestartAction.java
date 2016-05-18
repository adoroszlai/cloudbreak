package com.sequenceiq.cloudbreak.core.flow2;

import java.util.Collections;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.core.flow2.service.ErrorHandlerAwareFlowEventFactory;

import reactor.bus.Event;
import reactor.bus.EventBus;

@Component("DefaultRestartAction")
public class DefaultRestartAction implements RestartAction {

    @Inject
    private EventBus eventBus;

    @Inject
    private ErrorHandlerAwareFlowEventFactory eventFactory;

    @Override
    public void restart(String flowId, String event, Object payload) {
        Event.Headers headers = new Event.Headers(Collections.singletonMap("FLOW_ID", flowId));
        eventBus.notify(event, eventFactory.createEvent(headers, payload));
    }
}
