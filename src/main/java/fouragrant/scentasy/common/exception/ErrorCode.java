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
    EXTRA_INFO_DUPLICATED("4203", HttpStatus.CONFLICT, "추가정보가 존재하는 회원입니다."),
    FAILURE_LOGIN("4300", HttpStatus.UNAUTHORIZED, "잘못된 아이디 또는 비밀번호입니다."),
    EXTRA_INFO_NOT_FOUND("4301", HttpStatus.UNAUTHORIZED, "추가정보가 존재하지 않습니다."),
    TOKEN_EXPIRED("4400", HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    TOKEN_IN_BLACKLIST("4401", HttpStatus.UNAUTHORIZED, "블랙리스트 토큰." ),

    PERFUME_NOT_FOUND("4500", HttpStatus.BAD_REQUEST, "해당 향수가 존재하지 않습니다."),

    // community
    POST_NOT_FOUND("4600", HttpStatus.NOT_FOUND, "포스트가 존재하지 않습니다."),
    MEMBER_NOT_FOUND("4601", HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."),
    MEMBER_NOT_SAME("4602", HttpStatus.UNAUTHORIZED,"작성자와 일치하지 않습니다."),
    POST_LIKE_ALREADY_EXISTS("4603", HttpStatus.CONFLICT, "좋아요를 이미 눌렀습니다."),
    POST_LIKE_NO_EXISTS("4604", HttpStatus.UNAUTHORIZED, "좋아요를 누루지 않았습니다."),
    COMMENT_NOT_FOUND("4605", HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."),

    //calendar
    MEMO_NOT_FOUND("4700", HttpStatus.NOT_FOUND, "메모가 존재하지 않습니다." ),
    
    // file
    FILE_UPLOAD_FAILED("4800", HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."),
    FILE_EMPTY("4801", HttpStatus.BAD_REQUEST, "파일이 비어 있습니다."),
    FILE_SIZE_EXCEED("4802", HttpStatus.BAD_REQUEST, "파일 크기가 허용된 용량을 초과했습니다."),
    UNSUPPORTED_FILE_TYPE("4803", HttpStatus.BAD_REQUEST, "지원되지 않는 파일 형식입니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;


}
