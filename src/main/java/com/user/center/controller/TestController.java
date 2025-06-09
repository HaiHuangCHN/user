package com.user.center.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("/test")
public class TestController {


    @Autowired
    private RestTemplate restTemplate;

    /**
     * 消费者去访问具体服务，这种写法可以实现配置文件和代码的分离
     */
    @Value("${providers.order-center}")
    private String serverURL;

    @GetMapping(value = "getDefaultEchoMsg")
    public String getDefaultEchoMsg() {
        return restTemplate.getForObject(serverURL + "/test/getDefaultEchoMsg", String.class);
    }


}
