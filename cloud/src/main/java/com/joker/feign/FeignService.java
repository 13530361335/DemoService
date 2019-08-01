package com.joker.feign;

import com.joker.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @auther: Joker Jing
 * @date: 2019/7/29
 */
@FeignClient(value = "SPRINGBOOT")
public interface FeignService {

    /**
     * 调用第三方接口
     * @return
     */
    @GetMapping(value = "/test/sql")
    Result sql();

}