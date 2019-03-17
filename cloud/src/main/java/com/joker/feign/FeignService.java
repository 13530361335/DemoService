package com.joker.feign;

import com.joker.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "SPRINGBOOT")
public interface FeignService {

    @RequestMapping(value = "/test/sql", method = RequestMethod.GET)
    Result sql();

}