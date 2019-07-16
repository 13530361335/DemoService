package com.joker.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * created by Joker on 2019/7/16
 */
@ApiModel("注册参数")
@Setter
@Getter
@ToString(exclude = "password")
public class RegisterParameter {

    @ApiModelProperty(value = "手机号码")
    private String telephone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @NotBlank
    @ApiModelProperty(value = "验证码")
    private String verificationCode;

}
