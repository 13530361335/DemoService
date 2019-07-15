package com.joker.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * created by Joker on 2019/7/16
 */
@Setter
@Getter
@ToString(exclude = "password")
public class RegisterParameter {

    private String account;

    private String password;

    private String realName;

    private String telephone;

    private String email;

    private String verificationCode;

}
