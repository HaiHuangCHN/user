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

    @GetMapping(value = "/hello/{name}")
    public String apiHello(@PathVariable String name) {
        return testService.sayHello(name);
    }

}
