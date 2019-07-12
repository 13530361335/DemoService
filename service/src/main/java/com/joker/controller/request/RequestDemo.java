package com.joker.controller.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * created by Joker on 2019/7/12
 */
@Setter
@Getter
public class RequestDemo {

    @NotNull
    private Integer param1;

    @NotNull
    private BigDecimal param2;

    private String param3;

    private Date param4;

}
