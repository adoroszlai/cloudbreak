package com.sequenceiq.cloudbreak.converter;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.sequenceiq.cloudbreak.api.model.SecurityRuleRequest;
import com.sequenceiq.cloudbreak.controller.BadRequestException;
import com.sequenceiq.cloudbreak.domain.SecurityRule;

public class JsonToSecurityRuleConverterTest extends AbstractJsonConverterTest<SecurityRuleRequest> {

    private JsonToSecurityRuleConverter underTest;

    @Before
    public void setUp() {
        underTest = new JsonToSecurityRuleConverter();
    }

    @Test
    public void testConvert() {
        // GIVEN
        // WHEN
        SecurityRule result = underTest.convert(getRequest("security-group/security-rule.json"));
        // THEN
        assertAllFieldsNotNull(result, Collections.singletonList("securityGroup"));
    }

    @Test(expected = BadRequestException.class)
    public void testInvalidPorts() {
        underTest.convert(getRequest("security-group/invalid-rule.json"));
    }

    @Override
    public Class<SecurityRuleRequest> getRequestClass() {
        return SecurityRuleRequest.class;
    }
}
