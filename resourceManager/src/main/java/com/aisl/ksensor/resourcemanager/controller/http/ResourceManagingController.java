package com.aisl.ksensor.resourcemanager.controller.http;

import com.aisl.ksensor.resourcemanager.provisioning.vo.ResourceProvisioningNotiVO;
import com.aisl.ksensor.resourcemanager.sensorsetup.service.SensorSetupSVC;
import com.aisl.ksensor.resourcemanager.common.code.ResourceManagerCode.ProvisionEventType;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
public class ResourceManagingController {

    @Autowired
    private SensorSetupSVC sensorSetupSVC;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/provision/sensorsetup")
    public void createContainer(HttpServletRequest request,
                                HttpServletResponse response,
                                @RequestBody String requestBody) throws JsonProcessingException {

        log.info("createContainer");
        log.info("sensor resource CREATE req msg : {}", requestBody);

        // 1. 파라미터 파싱 및 유효성 검사
        ResourceProvisioningNotiVO resourceProvisioningNotiVO = objectMapper.readValue(requestBody, ResourceProvisioningNotiVO.class);

        ProvisionEventType provisionEventType = resourceProvisioningNotiVO.getEventType();
        switch(provisionEventType) {
            case CREATED:
                sensorSetupSVC.resourceCreate(resourceProvisioningNotiVO.getTo(), resourceProvisioningNotiVO.getProvisioningData(),
                        resourceProvisioningNotiVO.getRequestId(), resourceProvisioningNotiVO.getEventTime());
                response.setStatus(HttpStatus.CREATED.value());
                break;
            case UPDATED:
//                dataModelSVC.processUpdate(provisionNotiVO.getTo(), provisionNotiVO.getData(), provisionNotiVO.getRequestId(), provisionNotiVO.getEventTime());
//                response.setStatus(HttpStatus.NO_CONTENT.value());
                break;
            case DELETED:
//                dataModelSVC.processDelete(provisionNotiVO.getTo(), provisionNotiVO.getData(), provisionNotiVO.getRequestId(), provisionNotiVO.getEventTime());
//                response.setStatus(HttpStatus.NO_CONTENT.value());
                break;
            default:
                break;
        }



    }



}
