package com.joker.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Joker Jing
 * @date: 2019/7/29
 * @description:
 */
@ApiModel("请求参数实体")
@Setter
@Getter
public class RequestDemo {

    @ApiModelProperty(value = "参数1", required = true)
    @NotNull
    private Integer param1;

    @ApiModelProperty(value = "参数2", required = true)
    @NotNull
    private BigDecimal param2;

    @ApiModelProperty(value = "参数3", required = true)
    @NotNull
    private String param3;

    @ApiModelProperty(value = "参数4")
    @NotNull
    private Date param4;

}
