package com.sequenceiq.cloudbreak.repository;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.model.DetailedStackStatus;
import com.sequenceiq.cloudbreak.api.model.Status;
import com.sequenceiq.cloudbreak.cloud.store.InMemoryStateStore;
import com.sequenceiq.cloudbreak.converter.scheduler.StatusToPollGroupConverter;
import com.sequenceiq.cloudbreak.domain.Resource;
import com.sequenceiq.cloudbreak.domain.SecurityConfig;
import com.sequenceiq.cloudbreak.domain.Stack;
import com.sequenceiq.cloudbreak.domain.StackStatus;

@Component
public class StackUpdater {

    private static final Logger LOGGER = LoggerFactory.getLogger(StackUpdater.class);

    @Inject
    private StackStatusRepository stackStatusRepository;

    @Inject
    private StackRepository stackRepository;

    @Inject
    private ResourceRepository resourceRepository;

    @Inject
    private StatusToPollGroupConverter statusToPollGroupConverter;

    @Inject
    private SecurityConfigRepository securityConfigRepository;

    public Stack updateStackStatus(Long stackId, Status status) {
        return doUpdateStackStatus(stackId, status, DetailedStackStatus.UNKNOWN, "");
    }

    public Stack updateStackStatus(Long stackId, Status status, String statusReason) {
        return doUpdateStackStatus(stackId, status, DetailedStackStatus.UNKNOWN, statusReason);
    }

    public Stack updateStackStatus(Long stackId, Status status, DetailedStackStatus detailedStatus, String statusReason) {
        return doUpdateStackStatus(stackId, status, detailedStatus, statusReason);
    }

    public Stack addStackResources(Long stackId, List<Resource> resources) {
        Stack stack = stackRepository.findById(stackId);
        for (Resource resource : resources) {
            resource.setStack(stack);
        }
        resourceRepository.save(resources);
        stack.getResources().addAll(resources);
        return stackRepository.save(stack);
    }

    public void removeStackResources(List<Resource> resources) {
        resourceRepository.delete(resources);
    }

    public Stack updateStackSecurityConfig(Stack stack, SecurityConfig securityConfig) {
        securityConfig = securityConfigRepository.save(securityConfig);
        stack.setSecurityConfig(securityConfig);
        return stackRepository.save(stack);
    }

    private Stack doUpdateStackStatus(Long stackId, Status status, DetailedStackStatus detailedStatus, String statusReason) {
        Stack stack = stackRepository.findById(stackId);
//        if (stack.getStackStatus() != null) {
//            StackStatus statustest = stackStatusRepository.findOne(stack.getStackStatus().getId());
//            LOGGER.info("" + statustest.getStack());
//        }
        if (!stack.isDeleteCompleted()) {
            if (status != null) {
                stack.setStatus(status);
            }
            if (statusReason != null) {
                stack.setStatusReason(statusReason);
            }
            StackStatus stackStatus = stackStatusRepository.save(new StackStatus(stack, status, detailedStatus));
            stack.setStackStatus(stackStatus);
            InMemoryStateStore.putStack(stackId, statusToPollGroupConverter.convert(status));
            if (Status.DELETE_COMPLETED.equals(status)) {
                InMemoryStateStore.deleteStack(stackId);
            }
            stack = stackRepository.save(stack);
        }
        return stack;
    }

}
