package fouragrant.scentasy.common.exception;

import fouragrant.scentasy.common.Response;
import fouragrant.scentasy.common.dto.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<?> handleCommonException(CommonException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        Response<?> response = Response.createError(errorCode.getCode(), errorCode.getMessage());
        return new ResponseEntity<>(response, errorCode.getHttpStatus());
    }

}
