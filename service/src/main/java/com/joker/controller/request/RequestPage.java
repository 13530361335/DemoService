package com.joker.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author Joker Jing
 * @date: 2019/7/29
 * @description:
 */
@ApiModel("分页请求参数")
@Data
@NoArgsConstructor
public class RequestPage {

    private static final Long MIN_PAGE_SIZE = 10L;

    private static final Long MAX_PAGE_SIZE = 100L;

    @ApiModelProperty(value = "当前页数")
    private Long current = 1L;

    @ApiModelProperty(value = "每页大小")
    private Long size = 10L;

    public RequestPage(Long current, Long size) {
        if (current == null || current < 1) {
            this.current = 1L;
        }
        if (size == null || size < MIN_PAGE_SIZE || size > MAX_PAGE_SIZE) {
            this.size = 10L;
        }
    }

}
