package com.joker.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * created by Joker on 2019/7/28
 */
@ApiModel("分页请求参数")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RequestPage<T> {

    @ApiModelProperty(value = "当前页数", required = true)
    private Long current = 1L;

    @ApiModelProperty(value = "每页大小", required = true)
    private Long size = 10L;

    @ApiModelProperty(value = "查询条件")
    private T param;

    public RequestPage(T param) {
//        this.current = 1L;
//        this.size = 10L;
        this.param = param;
    }

    public RequestPage(Long current, Long size, T param) {
        if (current == null || current < 1) {
            this.current = 1L;
        }
        if (size == null || size < 10 || size > 100) {
            this.size = 10L;
        }
        this.param = param;
    }

}
