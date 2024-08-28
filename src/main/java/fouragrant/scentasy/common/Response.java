package fouragrant.scentasy.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private static final String SUCCESS_RESULT = "success";
    private static final String ERROR_RESULT = "error";

    private String message;
    private String code;
    private T data;

    public static <T> Response<T> createSuccessWithNoMessage(T data) {
        return new Response<>(SUCCESS_RESULT, null, data);
    }

    public static <T> Response<T> createSuccess(String code, T data) {
        return new Response<>(SUCCESS_RESULT, code, data);
    }

    public static Response<?> createSuccessWithNoData(String code) {
        return new Response<>(SUCCESS_RESULT, code, null);
    }

    public static Response<?> createError(String code, String message) {
        return new Response<>(ERROR_RESULT, code, null);
    }
}
