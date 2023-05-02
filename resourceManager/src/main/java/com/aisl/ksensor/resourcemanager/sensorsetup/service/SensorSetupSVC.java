package com.aisl.ksensor.resourcemanager.sensorsetup.service;

import com.aisl.ksensor.resourcemanager.common.code.ResourceManagerCode.ErrorCode;
import com.aisl.ksensor.resourcemanager.common.exception.BadRequestException;
import com.aisl.ksensor.resourcemanager.common.exception.BaseException;
import com.aisl.ksensor.resourcemanager.sensorsetup.vo.SensorSetupBaseVO;
import com.aisl.ksensor.resourcemanager.sensorsetup.dao.SensorSetupDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SensorSetupSVC {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SensorSetupDAO sensorSetupDAO;
    public static final String URI_PATTERN_CREATE_SENSOR_SETUP = "/sensorsetup";

    public void resourceCreate(String to, String requestBody, String requestId, Date eventTime) {

        if(URI_PATTERN_CREATE_SENSOR_SETUP.equals(to)) {
            createSensorResource(requestBody, requestId, eventTime);

            // 404
        } else {
            throw new BadRequestException(ErrorCode.NOT_EXIST_ID);
        }
    }

    private void createSensorResource(String requestBody, String requestId, Date eventTime) throws BaseException {

        // 1. 수신 데이터 파싱
        SensorSetupBaseVO sensorSetupBaseVO = null;
        try {
//            System.out.println("requestBody = " + requestBody);
            sensorSetupBaseVO = objectMapper.readValue(requestBody, SensorSetupBaseVO.class);
        } catch (IOException e) {
            throw new BadRequestException(ErrorCode.INVALID_PARAMETER,
                    "Invalid Parameter. body=" + requestBody);
        }

        System.out.println("sensorSetupBaseVO = " + sensorSetupBaseVO);
        // 2. 유효성 체크, todo.. validateParameter sensor setup에서 했으니 필요없을라나? 센서 셋업은?
//        validateParameter(sensorSetupBaseVO);
//
//        // 3. IoT Platform에 센서 생성 요청 및 결과 저장(CRUD 다 가능, train, test, target까지만 생성)
//        IoTPlatformResultVO ioTPlatformResultVO = iotPlatformManagingSVC.createSensorSetup(sensorSetupBaseVO, requestId, eventTime);


        // 4. 센서 생성 요청 결과 출력
//        log.info("Sensor Resource Create to IoT Platform Result. {}", ioTPlatformResultVO);

    }

}
