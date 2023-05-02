package com.aisl.ksensor.sensormanager.common.exception;

import com.aisl.ksensor.sensormanager.common.code.SensorManagerCode.ErrorCode;

//import java.io.Serial;

public abstract class BaseException extends RuntimeException {

//    @Serial
    private static final long serialVersionUID = 6697553987008675632L;

    ErrorCode errorCode = null;

    public BaseException( ErrorCode errorCode ) {
        this.errorCode = errorCode;
    }

    public BaseException( ErrorCode errorCode, String msg ) {
        super( msg );
        this.errorCode = errorCode;
    }

    public BaseException( ErrorCode errorCode, Throwable throwable ) {
        super( throwable );
        this.errorCode = errorCode;
    }

    public BaseException( ErrorCode errorCode, String msg, Throwable throwable ) {
        super( msg, throwable );
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode.getCode();
    }
}
