package com.websocket.websocketStudy.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import com.websocket.websocketStudy.apiPayload.code.BaseErrorCode;
import com.websocket.websocketStudy.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러"),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // Test
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "테스트"),

    // Page
    NOT_PAGE(HttpStatus.BAD_REQUEST, "PAGE4001", "페이지 요청이 잘못되었습니다. (page = 1 이상의 int)"),

    // ChatRoom
    ROOM_NOT_FOUND(HttpStatus.BAD_REQUEST, "ROOM4001", "존재하지 않는 채팅방입니다."),
    USER_ALREADY_IN_ROOM(HttpStatus.BAD_REQUEST, "ROOM4002", "이미 참여 중인 채팅방입니다."),
    INVALID_UUID(HttpStatus.BAD_REQUEST, "ROOM4003", "유효한 UUID 형식이 아닙니다."),
    USER_NOT_IN_ROOM(HttpStatus.BAD_REQUEST, "ROOM4004", "참여 중인 방이 아닙니다."),

    // User
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER4001", "존재하지 않는 사용자입니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "USER4002", "닉네임은 필수입니다."),
    NOT_AUTHENTICATED(HttpStatus.UNAUTHORIZED, "USER4003", "인증되지 않은 사용자입니다."),
    // Token
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4001", "유효하지 않은 토큰입니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "TOKEN4002", "토큰을 찾을 수 없습니다. (인증이 필요한 서비스)"),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN4003", "accessToken이 만료되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
