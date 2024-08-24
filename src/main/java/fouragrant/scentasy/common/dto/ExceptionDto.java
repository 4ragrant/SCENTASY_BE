package fouragrant.scentasy.common.dto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionDto {
    private final int code;
    private final String message;

    public ExceptionDto(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
