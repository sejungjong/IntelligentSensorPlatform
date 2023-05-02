package com.aisl.ksensor.sensormanager.sensorsetup.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorSetupBaseVO {
    private String id;
    private List<String> sensorId;
    private String name;
    private String sensorType;
    private String description; // sensor and sensor param description
    private List<String> sensorParameters;
    private List<String> nonValueParameters;
    private List<String> valueParameters;
    private List<String> discreteParameters;
    private List<String> continuousParameters;
    private List<String> categoricalParameters;
    private String sensorDuration;
    private Date createDatetime;
    private String creatorId;
}