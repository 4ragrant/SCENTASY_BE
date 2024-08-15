package fouragrant.scentasy.common.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseApiDto<T> {
    protected String code;
    protected String message;

    protected T result;

    protected BaseApiDto() {}

    public static BaseApiDto<Object> newBaseApiDto() {
        return new BaseApiDto<>();
    }

    public BaseApiDto(T result) {
        this.result = result;
    }
}
