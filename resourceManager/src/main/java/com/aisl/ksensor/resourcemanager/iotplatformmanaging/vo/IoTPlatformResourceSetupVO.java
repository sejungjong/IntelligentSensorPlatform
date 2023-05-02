package com.aisl.ksensor.resourcemanager.iotplatformmanaging.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IoTPlatformResourceSetupVO {

private String id;
    private String endpointUri;
    private String resourceUri;
    private String createDatetime;
    private String creatorId;
    private ApplicationEntityVO applicationEntity;
    private List<ContainerVO> containers;
//    todo.. m2m:cnt 뭐 이런거 어떻게 붙이지 고민
    
}
