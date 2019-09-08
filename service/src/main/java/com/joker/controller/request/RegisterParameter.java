package com.joker.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author Joker Jing
 * @date 2019/7/29
 * @description
 */
@ApiModel("注册参数")
@Setter
@Getter
public class RegisterParameter {

    @ApiModelProperty(value = "手机号码")
    private String telephone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @NotBlank
    @ApiModelProperty(value = "验证码")
    private String verificationCode;

}
