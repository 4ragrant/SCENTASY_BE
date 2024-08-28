package fouragrant.scentasy.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private static final String SUCCESS_RESULT = "success";
    private static final String ERROR_RESULT = "error";

    private String message;
    private Integer code;
    private T data;

    public static <T> Response<T> createSuccessWithNoMessage(T data) {
        return new Response<>(SUCCESS_RESULT, null, data);
    }

    public static <T> Response<T> createSuccess(Integer code, T data) {
        return new Response<>(SUCCESS_RESULT, code, data);
    }

    public static Response<?> createSuccessWithNoData(Integer code) {
        return new Response<>(SUCCESS_RESULT, code, null);
    }

    public static Response<?> createError(Integer code, String string) {
        return new Response<>(ERROR_RESULT, code, null);
    }
}
