package com.user.center.controller;

import com.user.center.remote.OrderCenterRemoteClient;
import com.user.center.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("/test")
public class TestController {


    private final OrderCenterRemoteClient orderCenterRemoteClient;

    private final TestService testService;

    @GetMapping(value = "getDefaultEchoMsg")
    public String getDefaultEchoMsg() {
        return orderCenterRemoteClient.getDefaultEchoMsg();
    }

    /**
     * Spring Cloud Alibaba Sentinel：https://github.com/alibaba/spring-cloud-alibaba/wiki/Sentinel
     * 在生产环境中使用 Sentinel：https://github.com/alibaba/Sentinel/wiki/%E5%9C%A8%E7%94%9F%E4%BA%A7%E7%8E%AF%E5%A2%83%E4%B8%AD%E4%BD%BF%E7%94%A8-Sentinel
     *
     * @param name
     * @return
     */
    @GetMapping(value = "/hello/{name}")
    public String apiHello(@PathVariable String name) {
        return testService.sayHello(name);
    }

}
