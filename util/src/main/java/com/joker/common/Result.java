package com.joker.common;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

/**
 * @author: Joker Jing
 * @date: 2019/7/29
 */
@Getter
@Setter
@ToString
public class Result<T> {

    private static final String MESSAGE_SUCCESS = "success";
    private static final int CODE_SUCCESS = 200;

    private static final String MESSAGE_FAIL = "fail";
    private static final int CODE_FAIL = 400;

    @JSONField(ordinal = 1)
    private int code;

    @JSONField(ordinal = 2)
    private String message;

    @JSONField(ordinal = 3)
    private T data;

    private Result(T data) {
        this.code = CODE_SUCCESS;
        this.message = MESSAGE_SUCCESS;
        this.data = data;
    }

    private Result(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = (T) new HashMap();
    }

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success() {
        return new Result<>(CODE_SUCCESS, MESSAGE_SUCCESS);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    public static <T> Result<T> fail(int code, String message) {
        return new Result<T>(code, message);
    }

    public static <T> Result<T> getInstance(int code, String message, T data) {
        return new Result<>(code, message, data);
    }

}
