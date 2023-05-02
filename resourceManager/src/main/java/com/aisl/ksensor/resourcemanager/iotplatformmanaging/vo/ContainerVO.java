package com.aisl.ksensor.resourcemanager.iotplatformmanaging.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContainerVO {

    @JsonProperty("rn")
    private String resourceName;

    @JsonProperty("lbl")
    private List<String> label;

    @JsonProperty("mbs")
    private Integer maxByteSize;
}
