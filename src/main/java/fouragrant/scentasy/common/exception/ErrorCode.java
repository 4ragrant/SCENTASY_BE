package fouragrant.scentasy.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    MISSING_PARAMETER("4100", HttpStatus.BAD_REQUEST, "파라메터 누락입니다."),
    VALIDATION_FAILED("4200", HttpStatus.BAD_REQUEST, "유효성 검증에 실패했습니다."),
    EMAIL_DUPLICATED("4201", HttpStatus.CONFLICT, "존재하는 이메일입니다."),
    NICKNAME_DUPLICATED("4202", HttpStatus.CONFLICT, "존재하는 닉네임입니다."),
    FAILURE_LOGIN("4300", HttpStatus.UNAUTHORIZED, "잘못된 아이디 또는 비밀번호입니다."),
    TOKEN_EXPIRED("4400", HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    TOKEN_IN_BLACKLIST("4401", HttpStatus.UNAUTHORIZED, "블랙리스트 토큰." );

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
