package com.sequenceiq.cloudbreak.core.flow2;

public interface RestartAction {

    void restart(String flowId, String event, Object payload);
}
