package com.aisl.ksensor.resourcemanager.common.exception;

//import java.io.Serial;
import com.aisl.ksensor.resourcemanager.common.code.ResourceManagerCode.ErrorCode;

public class BadRequestException extends BaseException {

    private static final long serialVersionUID = 325647261102179280L;

    public BadRequestException( ErrorCode errorCode ) {
        super( errorCode );
    }

    public BadRequestException( ErrorCode errorCode, String msg ) {
        super( errorCode, msg );
        this.errorCode = errorCode;
    }

    public BadRequestException( ErrorCode errorCode, Throwable throwable ) {
        super( errorCode, throwable );
        this.errorCode = errorCode;
    }

    public BadRequestException( ErrorCode errorCode, String msg, Throwable throwable ) {
        super( errorCode, msg, throwable );
        this.errorCode = errorCode;
    }
}