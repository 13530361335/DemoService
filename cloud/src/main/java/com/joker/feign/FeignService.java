package com.joker.feign;

import com.joker.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "SPRINGBOOT")
public interface FeignService {

    @GetMapping(value = "/test/sql")
    Result sql();

}