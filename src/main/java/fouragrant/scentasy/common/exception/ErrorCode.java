package fouragrant.scentasy.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    EMAIL_DUPLICATED(4201, HttpStatus.CONFLICT, "존재하는 이메일입니다."),
    FAILURE_LOGIN(4300, HttpStatus.UNAUTHORIZED, "잘못된 아이디 또는 비밀번호입니다."),
    EXPIRED_TOKEN_ERROR(4400, HttpStatus.UNAUTHORIZED, "만료된 토큰입니다.");

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;
}
