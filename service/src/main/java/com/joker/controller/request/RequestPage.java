package com.joker.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * created by Joker on 2019/7/28
 */
@ApiModel("分页请求参数")
@Data
@NoArgsConstructor
public class RequestPage {

    @ApiModelProperty(value = "当前页数")
    private Long current = 1L;

    @ApiModelProperty(value = "每页大小")
    private Long size = 10L;

    public RequestPage(Long current, Long size) {
        if (current == null || current < 1) {
            this.current = 1L;
        }
        if (size == null || size < 10 || size > 100) {
            this.size = 10L;
        }
    }

}
