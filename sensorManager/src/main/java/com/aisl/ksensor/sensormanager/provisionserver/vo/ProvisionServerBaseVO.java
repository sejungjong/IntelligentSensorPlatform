package com.aisl.ksensor.sensormanager.provisionserver.vo;

import com.aisl.ksensor.sensormanager.common.code.Constants;
import com.aisl.ksensor.sensormanager.common.code.SensorManagerCode.ProvisionServerType;
import com.aisl.ksensor.sensormanager.common.code.SensorManagerCode.ProvisionProtocol;
import com.aisl.ksensor.sensormanager.common.vo.PageRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
//public class ProvisionServerBaseVO extends PageRequest {
public class ProvisionServerBaseVO {

    private String id;
    private ProvisionServerType type;
    private String description;
    private String provisionUri;
    private ProvisionProtocol provisionProtocol;
    @JsonFormat(pattern = Constants.CONTENT_DATE_FORMAT)
    private Date createDatetime;
    private String creatorId;
    @JsonFormat(pattern = Constants.CONTENT_DATE_FORMAT)
    private Date modifyDatetime;
    private String modifierId;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public ProvisionServerType getType() {
        return type;
    }
    public void setType(ProvisionServerType type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getProvisionUri() {
        return provisionUri;
    }
    public void setProvisionUri(String provisionUri) {
        this.provisionUri = provisionUri;
    }
    public ProvisionProtocol getProvisionProtocol() {
        return provisionProtocol;
    }
    public void setProvisionProtocol(ProvisionProtocol provisionProtocol) {
        this.provisionProtocol = provisionProtocol;
    }
    public Date getCreateDatetime() {
        return createDatetime;
    }
    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }
    public String getCreatorId() {
        return creatorId;
    }
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
    public Date getModifyDatetime() {
        return modifyDatetime;
    }
    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }
    public String getModifierId() {
        return modifierId;
    }
    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }
}

