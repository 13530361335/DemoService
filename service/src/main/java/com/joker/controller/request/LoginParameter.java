package com.joker.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author Joker Jing
 * @date: 2019/7/29
 * @description:
 */
@ApiModel("登陆")
@Setter
@Getter
@ToString(exclude = "password")
public class LoginParameter {

    @NotBlank
    @ApiModelProperty(value = "账号", required = true)
    private String account;

    @NotBlank
    @ApiModelProperty(value = "密码", required = true)
    private String password;

}
