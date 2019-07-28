package com.joker.service.third;

import com.joker.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ThirdService {

    @Autowired
    private RestTemplate restTemplate;

    public Result doGet(){
        ResponseEntity<String> str = restTemplate.getForEntity("https://www.baidu.com",String.class);
        return new Result(str);
    }

}
