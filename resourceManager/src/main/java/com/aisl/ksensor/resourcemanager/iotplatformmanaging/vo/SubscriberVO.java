package com.aisl.ksensor.resourcemanager.iotplatformmanaging.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriberVO {

    @JsonProperty("rn")
    private String resourceName;

    @JsonProperty("enc")
    private EncVO enc;

    @JsonProperty("nu")
    private List<String> nu;

    @JsonProperty("exc")
    private Integer expirationCounter;

}
//{
//        "m2m:sub": {
//        "rn": "sub1",
//        "enc": {
//        "net": [3]
//        },
//        "nu": ["mqtt://203.250.148.120/MDAISLtarget?ct=json"],
//        "exc": 10
//        }
//        }