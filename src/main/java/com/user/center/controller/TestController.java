package com.user.center.controller;

import com.user.center.remote.OrderCenterRemoteClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("/test")
public class TestController {


    private final OrderCenterRemoteClient orderCenterRemoteClient;

    @GetMapping(value = "getDefaultEchoMsg")
    public String getDefaultEchoMsg() {
        return orderCenterRemoteClient.getDefaultEchoMsg();
    }


}
