package com.aisl.ksensor.resourcemanager.iotplatformmanaging.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationEntityVO {

    @JsonProperty("rn")
    private String resourceName;

    @JsonProperty("api")
    private String applicationId;

    @JsonProperty("lbl")
    private String label;

    @JsonProperty("rr")
    private Boolean requestReachability;

    @JsonProperty("poa")
    private String pointOfAccess;
}
