package com.sequenceiq.cloudbreak.api.model;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sequenceiq.cloudbreak.doc.ModelDescriptions.StackModelDescription;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@JsonIgnoreProperties(ignoreUnknown = true)
public class StackRequest extends StackBase {
    @Valid
    @ApiModelProperty(StackModelDescription.ORCHESTRATOR)
    private OrchestratorRequest orchestrator;
    @ApiModelProperty(value = StackModelDescription.AMBARI_HDP_VERSION)
    private String ambariHDPVersion;

    public OrchestratorRequest getOrchestrator() {
        return orchestrator;
    }

    public void setOrchestrator(OrchestratorRequest orchestrator) {
        this.orchestrator = orchestrator;
    }

    public String getAmbariHDPVersion() {
        return ambariHDPVersion;
    }

    public void setAmbariHDPVersion(String ambariHDPVersion) {
        this.ambariHDPVersion = ambariHDPVersion;
    }
}
