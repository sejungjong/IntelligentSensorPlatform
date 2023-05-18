package com.aisl.ksensor.resourcemanager.iotplatformmanaging.service;

import com.aisl.ksensor.resourcemanager.common.code.ResourceManagerCode.ErrorCode;
import com.aisl.ksensor.resourcemanager.common.code.ResourceManagerCode.ResourceType;
import com.aisl.ksensor.resourcemanager.common.exception.IoTPlatformException;
import com.aisl.ksensor.resourcemanager.iotplatformmanaging.vo.IoTPlatformResourceSetupVO;
import com.aisl.ksensor.resourcemanager.sensorsetup.vo.SensorSetupBaseVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class IoTPlatformResourceImpl implements IoTPlatformResourceInterface {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${kafka.topic.change.event.datamodel}")
    private String ioTPlatformEntrypointUri;
    @Value("${kafka.topic.change.event.acl-rule}")
    private String aclRuleChangeEventTopic;


    @Override
    public boolean createResource(String endpointUri, String resourceUri, String resourceType, String resourceValue) {
        return false;
    }

    @Override
    public List<String> retrieveResource(String endpointUri, String resourceUri) {
        return null;
    }


    private IoTPlatformResourceSetupVO SetupToResource(String requestId, Date eventTime, SensorSetupBaseVO sensorSetupBaseVO) {

        IoTPlatformResourceSetupVO ioTPlatformResourceSetupVO = new IoTPlatformResourceSetupVO();

        ioTPlatformResourceSetupVO.setId(UUID.randomUUID().toString());
        ioTPlatformResourceSetupVO.setCreateDatetime(new Date());

        ioTPlatformResourceSetupVO.setProvisionEventId(requestId);
        ioTPlatformResourceSetupVO.setProvisionEventTime(eventTime);

        ioTPlatformResourceSetupVO.setSetupId(sensorSetupBaseVO.getId());
        ioTPlatformResourceSetupVO.setSetupTime(sensorSetupBaseVO.getCreateDatetime());
        ioTPlatformResourceSetupVO.setCreatorId(sensorSetupBaseVO.getCreatorId());


        //        1. 센서 셋업 리소스 정보 생성
        ioTPlatformResourceSetupVO.setResourceUri();

        ioTPlatformResourceSetupVO.setApplicationEntity();
        ioTPlatformResourceSetupVO.setContainers();
        ioTPlatformResourceSetupVO.setSubscribers();

        return ioTPlatformResourceSetupVO;
    }

    public boolean IoTPlatformRequest(String endpointUri, ResourceType resourceType, String sendMessage) throws IoTPlatformException {

        try {
//            System.out.println(endpointUri);
//            endpointUri = "http://203.250.148.120:20519/Mobius";
            // header 설정

            log.debug("Send HTTP Provisioning. endpointUri={}, sendMessage={}", endpointUri, sendMessage);

            // header 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<>(sendMessage, headers);

            ResponseEntity<String> responseEntity = null;
            long startTime = System.currentTimeMillis();
            try {
                responseEntity = restTemplate.postForEntity(endpointUri, entity, String.class);
            } catch (HttpClientErrorException e) {
                throw new IoTPlatformException(ErrorCode.PROVISIONING_ERROR, e.getRawStatusCode(), "Provisioning error. " + e.getResponseBodyAsString());
            }
            long elapsedTime = System.currentTimeMillis() - startTime;

            if (responseEntity == null) {
                throw new IoTPlatformException(ErrorCode.PROVISIONING_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Provisioning error. HTTP response is null");
            } else if (responseEntity.getStatusCode() != HttpStatus.OK
                    && responseEntity.getStatusCode() != HttpStatus.CREATED
                    && responseEntity.getStatusCode() != HttpStatus.NO_CONTENT) {
                throw new IoTPlatformException(ErrorCode.PROVISIONING_ERROR,
                        responseEntity.getStatusCodeValue(), "Provisioning error." + responseEntity.getBody());
            } else {
                log.info("HTTP sendProvisionData. StatusCode={}, sendMessage={}, elapsedTime={}ms",
                        responseEntity.getStatusCodeValue(), sendMessage, elapsedTime);
            }

            return true;

        } catch (IoTPlatformException e) {
            throw e;
        } catch (Exception e) {
            throw new IoTPlatformException(ErrorCode.PROVISIONING_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Provisioning error", e);
        }
    }

}
