package fouragrant.scentasy.common.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class BaseApiController<T extends BaseApiDto<?>> {
    protected ResponseEntity<T> ok(T t) {
        setSuccessRtValues(t);
        return ResponseEntity.ok().body(t);
    }

    protected <T2 extends BaseApiDto<?>> ResponseEntity<T2> ok(T2 t, Class<T2> claszz) {
        setSuccessRtValues(t);
        return ResponseEntity.ok().body(t);
    }

    protected <T4> ResponseEntity<List<T4>> ok(List<T4> t) {
        return ResponseEntity.ok().body(t);
    }

    protected ResponseEntity<T> created(T t) {
        setSuccessRtValues(t);
        return new ResponseEntity<>(t, HttpStatus.CREATED);
    }

    protected <T2 extends BaseApiDto<?>> ResponseEntity<T2> created(T2 t, Class<T2> claszz) {
        setSuccessRtValues(t);
        return new ResponseEntity<>(t, HttpStatus.CREATED);
    }

    protected ResponseEntity<T> fail(T t) {
        setFailRtValues(t);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(t);
    }

    protected <T2 extends BaseApiDto<?>> ResponseEntity<T2> fail(T2 t, Class<T2> claszz) {
        setFailRtValues(t);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(t);
    }

    protected ResponseEntity<T> fail(T t, String code, String message) {
        t.setCode(code);
        t.setMessage(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(t);
    }

    protected <T2 extends BaseApiDto<?>> ResponseEntity<T2> fail(T2 t, String code, String message, Class<T2> claszz) {
        t.setCode(code);
        t.setMessage(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(t);
    }

    protected ResponseEntity<T> notFound(T t, String code, String message) {
        t.setCode(code);
        t.setMessage(message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(t);
    }

    protected <T2 extends BaseApiDto<?>> ResponseEntity<T2> notFound(T2 t, String code, String message, Class<T2> claszz) {
        t.setCode(code);
        t.setMessage(message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(t);
    }

    protected ResponseEntity<T> invalidParam(T t, String code, String message) {
        t.setCode(code);
        t.setMessage(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(t);
    }

    protected <T2 extends BaseApiDto<?>> ResponseEntity<T2> invalidParam(T2 t, String code, String message, Class<T2> claszz) {
        t.setCode(code);
        t.setMessage(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(t);
    }

    private <T3 extends BaseApiDto<?>> void setSuccessRtValues(T3 t) {
        t.setCode("0000");
        t.setMessage("SUCCESS");
    }

    private <T3 extends BaseApiDto<?>> void setFailRtValues(T3 t) {
        if (StringUtils.isBlank(t.getCode())) {
            t.setCode("9999");
        }
        if (StringUtils.isBlank(t.getMessage())) {
            t.setMessage("FAIL");
        }
    }
}
