package com.joker.common;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author: Joker Jing
 * @date: 2019/7/29
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Result<T> {

    @JSONField(ordinal = 1)
    private int code;

    @JSONField(ordinal = 2)
    private String message;

    @JSONField(ordinal = 3)
    private T data;

    public Result() {
        this.code = 200;
        this.message = "success";
    }

    public Result(T data) {
        this.code = 200;
        this.message = "success";
        this.data = data;
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = (T) new Object();
    }

}
