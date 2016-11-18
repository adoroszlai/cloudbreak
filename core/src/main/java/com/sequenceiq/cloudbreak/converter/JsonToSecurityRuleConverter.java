package com.sequenceiq.cloudbreak.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.api.model.SecurityRuleRequest;
import com.sequenceiq.cloudbreak.controller.BadRequestException;
import com.sequenceiq.cloudbreak.domain.SecurityRule;

@Component
public class JsonToSecurityRuleConverter extends AbstractConversionServiceAwareConverter<SecurityRuleRequest, SecurityRule> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonToSecurityRuleConverter.class);

    private static final int SSH_PORT = 22;

    private static final int HTTPS_PORT = 443;

    private static final int MIN_RANGE = 1;

    private static final int MAX_RANGE = 65535;

    @Override
    public SecurityRule convert(SecurityRuleRequest json) {
        SecurityRule entity = new SecurityRule();
        entity.setCidr(json.getSubnet());
        String ports = json.getPorts();
        validatePorts(ports);
        entity.setPorts(ports);
        entity.setProtocol(json.getProtocol());
        entity.setModifiable(json.isModifiable());
        return entity;
    }

    private void validatePorts(String ports) {
        boolean port22 = false;
        boolean port443 = false;
        for (String portString : ports.split(",")) {
            try {
                Integer port = Integer.valueOf(portString);
                if (port.equals(SSH_PORT)) {
                    port22 = true;
                } else if (port.equals(HTTPS_PORT)) {
                    port443 = true;
                } else if (port < MIN_RANGE || port > MAX_RANGE) {
                    throw new BadRequestException(String.format("Ports must be in range of %d-%d", MIN_RANGE, MAX_RANGE));
                }
            } catch (NumberFormatException e) {
                LOGGER.warn("Invalid port provided, skipping..", e);
            }
        }
        if (!port22 || !port443) {
            throw new BadRequestException(String.format("%d and %d ports must be open", SSH_PORT, HTTPS_PORT));
        }
    }
}
