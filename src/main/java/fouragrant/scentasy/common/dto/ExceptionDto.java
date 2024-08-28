package fouragrant.scentasy.common.dto;
import lombok.Getter;

@Getter
public class ExceptionDto {
    private final String code;
    private final String message;

    public ExceptionDto(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
