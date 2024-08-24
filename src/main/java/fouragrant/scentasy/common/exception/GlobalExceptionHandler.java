package fouragrant.scentasy.common.exception;

import fouragrant.scentasy.common.dto.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ExceptionDto> handleCommonException(CommonException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ExceptionDto response = new ExceptionDto(errorCode.getCode(), errorCode.getMessage());
        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }

}
