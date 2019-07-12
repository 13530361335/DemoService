package com.joker.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * created by Joker on 2019/7/12
 */
@Setter
@Getter
@AllArgsConstructor
public class RequestException extends Exception {

    private int code;
    private String message;

}
