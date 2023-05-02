package com.aisl.ksensor.resourcemanager.common.exception;

import com.aisl.ksensor.resourcemanager.common.code.ResourceManagerCode.ErrorCode;

public class IoTPlatformException extends BaseException{
/**
 * IoTPlatformException 에러 정의 클래스
 */

    private static final long serialVersionUID = 1197788900905860252L;
    private Integer IoTPlatformStatusCode;

    public IoTPlatformException(ErrorCode errorCode, Integer provisioningStatusCode) {
        super( errorCode );
        this.IoTPlatformStatusCode = provisioningStatusCode;
    }

    public IoTPlatformException( ErrorCode errorCode, Integer provisioningStatusCode, String msg) {
        super( errorCode, msg );
        this.IoTPlatformStatusCode = provisioningStatusCode;
        this.errorCode = errorCode;
    }

    public IoTPlatformException( ErrorCode errorCode, Integer provisioningStatusCode, Throwable throwable ) {
        super( errorCode, throwable );
        this.IoTPlatformStatusCode = provisioningStatusCode;
        this.errorCode = errorCode;
    }

    public IoTPlatformException( ErrorCode errorCode, Integer provisioningStatusCode, String msg, Throwable throwable ) {
        super( errorCode, msg, throwable );
        this.IoTPlatformStatusCode = provisioningStatusCode;
        this.errorCode = errorCode;
    }

    public Integer getIoTPlatformStatusCode() {
        return IoTPlatformStatusCode;
    }
}
