package com.aisl.ksensor.sensormanager.sensorsetup.service;

import com.aisl.ksensor.sensormanager.common.code.SensorManagerCode;
import com.aisl.ksensor.sensormanager.common.code.SensorManagerCode.ProvisionServerType;
import com.aisl.ksensor.sensormanager.common.code.SensorManagerCode.ProvisionEventType;
import com.aisl.ksensor.sensormanager.common.exception.BadRequestException;
import com.aisl.ksensor.sensormanager.common.code.SensorManagerCode.ProvisioningSubUri;
//import com.aisl.ksensor.sensormanager.provisioning.service.ProvisioningSVC.KafkaProvisioningType;
import com.aisl.ksensor.sensormanager.provisioning.service.ProvisioningSVC;
import com.aisl.ksensor.sensormanager.provisioning.vo.ProvisionResultVO;
import com.aisl.ksensor.sensormanager.provisioning.vo.ResourceProvisioningNotiVO;
import com.aisl.ksensor.sensormanager.provisionserver.vo.ProvisionServerBaseVO;
import com.aisl.ksensor.sensormanager.provisionserver.vo.ProvisionServerSVC;
import com.aisl.ksensor.sensormanager.sensorsetup.dao.SensorSetupDAO;
import com.aisl.ksensor.sensormanager.sensorsetup.vo.SensorSetupBaseVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class SensorSetupSVC {

    @Autowired
    private SensorSetupDAO sensorSetupDAO;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProvisionServerSVC provisionServerSVC;
    @Autowired
    private ProvisioningSVC provisioningSVC;


    public SensorSetupBaseVO getDataModelBaseVOById(String id) {
        SensorSetupBaseVO sensorSetupBaseVO = new SensorSetupBaseVO();
        sensorSetupBaseVO.setId(id);
        return sensorSetupDAO.getSensorSetupBaseVO(sensorSetupBaseVO);
    }

    public int createSensorSetupBaseVO(SensorSetupBaseVO sensorSetupBaseVO) {
        System.out.println("sensorSetupBaseVO is" + sensorSetupBaseVO);
        return sensorSetupDAO.createSensorSetupVO(sensorSetupBaseVO);
    }


    public ResourceProvisioningNotiVO provisionSensorSetup(ProvisionEventType provisionEventType, String requestURI, SensorSetupBaseVO sensorSetupBaseVO) {
        ResourceProvisioningNotiVO resourceProvisioningNotiVO = new ResourceProvisioningNotiVO();

        resourceProvisioningNotiVO.setRequestId(UUID.randomUUID().toString());
        resourceProvisioningNotiVO.setEventTime(new Date());
        resourceProvisioningNotiVO.setEventType(provisionEventType);
        resourceProvisioningNotiVO.setTo(requestURI);

        try {
//            System.out.println("[sensorSetupBaseVO is]" + objectMapper.writeValueAsString(sensorSetupBaseVO));
            resourceProvisioningNotiVO.setProvisioningData(objectMapper.writeValueAsString(sensorSetupBaseVO));
//            System.out.println("[resourceProvisioningNotiVO provisioningdata is]" + resourceProvisioningNotiVO.getProvisioningData());
        } catch (IOException e) {
            throw new BadRequestException(SensorManagerCode.ErrorCode.UNKNOWN_ERROR, "SensorSetup parsing error. body=" + sensorSetupBaseVO);
        }
        provisionSensorSetup(resourceProvisioningNotiVO, ProvisionServerType.RESOURCE_MANAGER);


        return resourceProvisioningNotiVO;

    }

    private void provisionSensorSetup(ResourceProvisioningNotiVO resourceProvisioningNotiVO, ProvisionServerType provisionServerType) {
        // 1. Provisioning 대상 서버 조회
        ProvisionServerBaseVO provisionServerVO = provisionServerSVC.getProvisionServerVOByTypeLatest(provisionServerType);

        log.info("Sensor Setup Provisioning Server by Type. {}", provisionServerVO);

        // 2. Provisioning 서버에 센서 설치 정보를 등록
        ProvisionResultVO provisionResultVO = provisioningSVC.provisioning(provisionServerType,
                provisionServerVO, resourceProvisioningNotiVO, ProvisioningSubUri.SENSOR_MANAGER);


        log.info("Sensor Setup Provisioning Result. {}", provisionResultVO);

        // 3. 결과 처리
        boolean processResult = false;

        // Provisionig 대상 미 존재하므로 성공 처리
        if(provisionResultVO.getResult()) {
            processResult = true;
        } else {
            throw provisionResultVO.getProvisionException();
        }
    }



}

