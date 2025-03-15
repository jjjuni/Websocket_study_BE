package com.websocket.websocketStudy.apiPayload.exception;

import com.websocket.websocketStudy.apiPayload.code.BaseErrorCode;

public class ErrorException extends GeneralException {

    public ErrorException(BaseErrorCode errorCode){
        super(errorCode);
    }
}
