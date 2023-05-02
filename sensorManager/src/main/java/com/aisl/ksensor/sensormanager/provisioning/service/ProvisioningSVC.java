package com.aisl.ksensor.sensormanager.provisioning.service;

import com.aisl.ksensor.sensormanager.common.code.SensorManagerCode.ProvisioningSubUri;
import com.aisl.ksensor.sensormanager.common.code.SensorManagerCode.ProvisionServerType;
import com.aisl.ksensor.sensormanager.common.code.SensorManagerCode.ProvisionProtocol;
import com.aisl.ksensor.sensormanager.common.code.SensorManagerCode.ErrorCode;
import com.aisl.ksensor.sensormanager.common.exception.ProvisionException;
import com.aisl.ksensor.sensormanager.provisioning.vo.ProvisionResultVO;
import com.aisl.ksensor.sensormanager.provisionserver.vo.ProvisionServerBaseVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import com.aisl.ksensor.sensormanager.provisioning.vo.ResourceProvisioningNotiVO;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Slf4j
public class ProvisioningSVC {

    @Autowired
    private RestTemplate restTemplate;
//    @Autowired
//    private KafkaProducerManager kafkaProducerManager;
    @Autowired
    private ObjectMapper objectMapper;


    public ProvisionResultVO provisioning(ProvisionServerType provisionServerType,
                                          ProvisionServerBaseVO provisionServerBaseVO,
                                          ResourceProvisioningNotiVO resourceProvisioningNotiVO,
                                          ProvisioningSubUri subUrl) {

        if (resourceProvisioningNotiVO != null) {


            // 1. Provisioning 결과 정보 생성
            ProvisionResultVO provisionResultVO = new ProvisionResultVO();
            provisionResultVO.setRequestId(resourceProvisioningNotiVO.getRequestId());
            provisionResultVO.setEventTime(resourceProvisioningNotiVO.getEventTime());
            System.out.println("println ! " + resourceProvisioningNotiVO.getEventTime());
            System.out.println("println ! " + provisionServerBaseVO.getId());

            provisionResultVO.setProvisionServerId(provisionServerBaseVO.getId());
            provisionResultVO.setProvisionEventType(resourceProvisioningNotiVO.getEventType());
            provisionResultVO.setProvisionServerType(provisionServerType);

            try {
                // 2. Provisioning 전송
                boolean result = sendProvisionData(
                        provisionServerBaseVO.getProvisionProtocol(),
                        provisionServerBaseVO.getProvisionUri() + subUrl.getCode(),
                        resourceProvisioningNotiVO);

                provisionResultVO.setResult(result);

            } catch (ProvisionException e) {
                log.warn("Provisioning error. provisionServerId={}, eventType={}", provisionServerBaseVO.getId(), provisionResultVO.getProvisionEventType().getCode(), e);
                provisionResultVO.setResult(false);
                provisionResultVO.setProvisionException(e);

            } catch (Exception e) {
                log.warn("Provisioning error. provisionServerId={}, eventType={}", provisionServerBaseVO.getId(), provisionResultVO.getProvisionEventType().getCode(), e);
                provisionResultVO.setResult(false);
                provisionResultVO.setProvisionException(
                        new ProvisionException(ErrorCode.PROVISIONING_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e));
            }

            return provisionResultVO;
        }

        return null;


    }

    /**
     * Provisioning 데이터 전송
     * @param provisionProtocol Provisioning 프로토콜 (http or kafka)
     * @param endpointUri       목적지 uri
     * @param resourceProvisioningNotiVO   Provisioning 전송 데이터 VO
     * @return 전송 결과
     * @throws ProvisionException 파싱 혹은 통신 Exception
     */
    public boolean sendProvisionData(ProvisionProtocol provisionProtocol, String endpointUri,
                                     ResourceProvisioningNotiVO resourceProvisioningNotiVO) throws ProvisionException {

        try {
//            System.out.println(endpointUri);
//            http://resourceManager:9091/provision/sensorsetup
            endpointUri = "http://localhost:9091/provision/sensorsetup";

            // 1. 전송 데이터 Json 으로 파싱
            String sendMessage = objectMapper.writeValueAsString(resourceProvisioningNotiVO);

            // 2. HTTP 데이터 전송
            if (provisionProtocol == ProvisionProtocol.HTTP) {

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
                    throw new ProvisionException(ErrorCode.PROVISIONING_ERROR, e.getRawStatusCode(), "Provisioning error. " + e.getResponseBodyAsString());
                }
                long elapsedTime = System.currentTimeMillis() - startTime;

                if (responseEntity == null) {
                    throw new ProvisionException(ErrorCode.PROVISIONING_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Provisioning error. HTTP response is null");
                } else if (responseEntity.getStatusCode() != HttpStatus.OK
                        && responseEntity.getStatusCode() != HttpStatus.CREATED
                        && responseEntity.getStatusCode() != HttpStatus.NO_CONTENT) {
                    throw new ProvisionException(ErrorCode.PROVISIONING_ERROR,
                            responseEntity.getStatusCodeValue(), "Provisioning error." + responseEntity.getBody());
                } else {
                    log.info("HTTP sendProvisionData. StatusCode={}, sendMessage={}, elapsedTime={}ms",
                            responseEntity.getStatusCodeValue(), sendMessage, elapsedTime);
                }

                return true;

            } else if (provisionProtocol == ProvisionProtocol.KAFKA) {
                throw new ProvisionException(ErrorCode.PROVISIONING_ERROR, HttpStatus.BAD_REQUEST.value(), "Unsupported ProvisionProtocol. protocol=" + provisionProtocol);
            } else {
                throw new ProvisionException(ErrorCode.PROVISIONING_ERROR, HttpStatus.BAD_REQUEST.value(), "Unsupported ProvisionProtocol. protocol=" + provisionProtocol);
            }

        } catch (ProvisionException e) {
            throw e;
        } catch (Exception e) {
            throw new ProvisionException(ErrorCode.PROVISIONING_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Provisioning error", e);
        }
    }

    //    public static enum KafkaProvisioningType {
//        SENSOR_SETUP;
//    }

//    public void sendKafkaEvent(KafkaProvisioningType provisioningTarget, ResourceProvisioningNotiVO provisionNotiVO) {
//        if (provisioningTarget == null || provisionNotiVO == null) {
//            return;
//        }

    //        String topic = null;
//        if (provisioningTarget == KafkaProvisioningType.DATA_MODEL) {
//            topic = dataModelChangeEventTopic;
//        } else if (provisioningTarget == KafkaProvisioningType.DATASET) {
//            topic = datasetChangeEventTopic;
//        } else if (provisioningTarget == KafkaProvisioningType.ACL_RULE) {
//            topic = aclRuleChangeEventTopic;
//        } else {
//            return;
//        }
//
//        KafkaProcessVO kafkaProcessVO = new KafkaProcessVO();
//        kafkaProcessVO.setProvisionNotiVO(provisionNotiVO);
//        kafkaProcessVO.setTopic(topic);
//
//        try {
//            kafkaProducerManager.produceData(kafkaProcessVO);
//        } catch (Exception e) {
//            log.error("Kafka Provisioning error.", e);
//        }

//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
}
