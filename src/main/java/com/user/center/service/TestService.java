package com.user.center.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @SentinelResource(value = "sayHello")
    public String sayHello(String name) {
        return "Hello, " + name;
    }

}