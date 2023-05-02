package com.aisl.ksensor.sensormanager.provisioning.vo;

import com.aisl.ksensor.sensormanager.common.code.Constants;
import com.aisl.ksensor.sensormanager.common.code.SensorManagerCode.ProvisionEventType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@Data
public class ResourceProvisioningNotiVO {
    @JsonFormat(pattern = Constants.CONTENT_DATE_FORMAT)
    private String requestId;
    private Date eventTime;
    private String to;
    private ProvisionEventType eventType;

    private String provisioningData;

//    public void setProvisionEventType(ProvisionEventType provisionEventType) {
//    }
//
//    public void setProvisioningData(String provisioningData) {
//    }


//    public String getRequestId() {
//        return requestId;
//    }
//    public void setRequestId(String requestId) {
//        this.requestId = requestId;
//    }
//    public Date getEventTime() {
//        return eventTime;
//    }
//    public void setEventTime(Date eventTime) {
//        this.eventTime = eventTime;
//    }
//    public String getTo() {
//        return to;
//    }
//    public void setTo(String to) { this.to = to; }
//    public ProvisionEventType getEventType() {
//        return eventType;
//    }
//    public void setEventType(ProvisionEventType eventType) {
//        this.eventType = eventType;
//    }
//    public String getProvisioningData() { return provisioningData; }
//    public void setProvisioningData(Object provisioningData) {
//        try {
//            this.provisioningData = provisioningData.toString();
//        } catch (Exception e) {
//            log.error("setProvisioningData error", e);
//        }
//    }

}
