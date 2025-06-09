package com.user.center.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author hai.huang.a@outlook.com
 * @date 2023/7/28 20:30
 */
@FeignClient(name = "order-center", fallbackFactory = OrderCenterRemoteClientFallbackFactory.class)
public interface OrderCenterRemoteClient {

    @GetMapping(value = "/test/getDefaultEchoMsg")
    String getDefaultEchoMsg();

}
