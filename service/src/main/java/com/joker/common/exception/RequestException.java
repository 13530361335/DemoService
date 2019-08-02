package com.joker.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Joker Jing
 * @date: 2019/7/29
 * @description: 统一常量存放
 */
@Setter
@Getter
@AllArgsConstructor
public class RequestException extends Exception {

    private int code;
    private String message;


}
