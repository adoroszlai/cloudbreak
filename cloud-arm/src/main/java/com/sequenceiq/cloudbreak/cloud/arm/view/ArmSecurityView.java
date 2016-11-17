package com.sequenceiq.cloudbreak.cloud.arm.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sequenceiq.cloudbreak.cloud.model.Group;
import com.sequenceiq.cloudbreak.cloud.model.PortDefinition;
import com.sequenceiq.cloudbreak.cloud.model.SecurityRule;

public class ArmSecurityView {

    private Map<String, List<ArmPortView>> ports = new HashMap<>();

    public ArmSecurityView(List<Group> groups) {
        for (Group group : groups) {
            List<ArmPortView> groupPorts = new ArrayList<>();
            for (SecurityRule securityRule : group.getSecurity().getRules()) {
                for (PortDefinition port : securityRule.getPorts()) {
                    if (port.getFrom().equals(port.getTo())) {
                        groupPorts.add(new ArmPortView(securityRule.getCidr(), port.getFrom(), securityRule.getProtocol()));
                    } else {
                        Integer portFrom = Integer.parseInt(port.getFrom());
                        Integer portTo = Integer.parseInt(port.getTo());
                        for (int i = portFrom; i < portTo; i++) {
                            groupPorts.add(new ArmPortView(securityRule.getCidr(), String.valueOf(i), securityRule.getProtocol()));
                        }
                    }
                }
            }
            ports.put(group.getName(), groupPorts);
        }
    }

    public Map<String, List<ArmPortView>> getPorts() {
        return ports;
    }

}