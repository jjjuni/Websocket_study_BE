package com.websocket.websocketStudy.apiPayload.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.websocket.websocketStudy.apiPayload.code.BaseErrorCode;
import com.websocket.websocketStudy.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private final BaseErrorCode code;

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus() {
        return this.code.getReasonHttpStatus();
    }
}
