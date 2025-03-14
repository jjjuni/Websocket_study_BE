package com.websocket.websocketStudy.apiPayload.exception;

import com.websocket.websocketStudy.apiPayload.code.BaseErrorCode;

public class TempException extends GeneralException {

    public TempException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
