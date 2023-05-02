package com.aisl.ksensor.resourcemanager.provisioning.vo;

import com.aisl.ksensor.resourcemanager.common.code.Constants;
import com.aisl.ksensor.resourcemanager.common.code.ResourceManagerCode.ProvisionEventType;
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

}

