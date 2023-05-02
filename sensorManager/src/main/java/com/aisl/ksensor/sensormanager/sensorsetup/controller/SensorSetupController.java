package com.aisl.ksensor.sensormanager.sensorsetup.controller;

import com.aisl.ksensor.sensormanager.common.code.SensorManagerCode.ErrorCode;
import com.aisl.ksensor.sensormanager.common.exception.BadRequestException;
import com.aisl.ksensor.sensormanager.provisioning.vo.ResourceProvisioningNotiVO;
import com.aisl.ksensor.sensormanager.sensorsetup.vo.SensorSetupBaseVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aisl.ksensor.sensormanager.sensorsetup.service.SensorSetupSVC;
import com.aisl.ksensor.sensormanager.util.ValidateUtil;
import com.aisl.ksensor.sensormanager.common.code.SensorManagerCode.ProvisionEventType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 * 센서셋업 정보 관리 HTTP Controller 클래스
 *  - 센서셋업 정보를 생성/수정/삭제하고 기 등록된 Resource Provisioning 대상 서버로 생성/수정/삭제 이벤트를 Provisioning 한다
 *  - 센서 셋업 정보 조회 기능을 제공한다
 * </pre>
 */

@RestController
@Slf4j
public class SensorSetupController {

    @Autowired
    private SensorSetupSVC sensorSetupSVC;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/sensorsetup")
    @Transactional
    public void createSensorSetup(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @RequestBody String requestBody) throws JsonProcessingException {

//        ui에서는 전체파라미터를 파싱해서 센서파라미터만 보내게 해야되나?
        log.info("createSensorSetup");
        log.info("sensorSetup CREATE req msg : {}", requestBody);

        SensorSetupBaseVO sensorSetupBaseVO = objectMapper.readValue(requestBody, SensorSetupBaseVO.class);

        validateParameter(sensorSetupBaseVO);


        SensorSetupBaseVO retrieveSensorSetupBaseVO = sensorSetupSVC.getDataModelBaseVOById(sensorSetupBaseVO.getId());


        if (retrieveSensorSetupBaseVO != null) {
            throw new BadRequestException(ErrorCode.ALREADY_EXISTS,
                    "Already Exists. Service Name=" + retrieveSensorSetupBaseVO.getName() + ", Sensor type=" + retrieveSensorSetupBaseVO.getSensorType());
        }


        ResourceProvisioningNotiVO resourceProvisioningNotiVO = sensorSetupSVC.provisionSensorSetup(ProvisionEventType.CREATED, request.getRequestURI(), sensorSetupBaseVO);



        sensorSetupSVC.createSensorSetupBaseVO(sensorSetupBaseVO);

    }

    private void validateParameter(SensorSetupBaseVO sensorSetupVO) {
        if(ValidateUtil.isEmptyData(sensorSetupVO.getId())) {
            throw new BadRequestException(ErrorCode.INVALID_PARAMETER, "Not exists id.");
        }
        if(ValidateUtil.isEmptyData(sensorSetupVO.getSensorParameters())) {
            throw new BadRequestException(ErrorCode.INVALID_PARAMETER, "Not exists sensorparameters.");
        }
        if(ValidateUtil.isEmptyData(sensorSetupVO.getSensorDuration())) {
            throw new BadRequestException(ErrorCode.INVALID_PARAMETER, "Not exists type.");
        }
    }

}
