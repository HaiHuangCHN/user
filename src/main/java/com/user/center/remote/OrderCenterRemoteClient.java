package com.user.center.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 注（2025-06-10）：相比 fallback 的写法，我更倾向 fallbackFactory 的写法
 *
 * @author hai.huang.a@outlook.com
 * @date 2023/7/28 20:30
 */
@FeignClient(name = "order-center", fallbackFactory = OrderCenterRemoteClientFallbackFactory.class)
public interface OrderCenterRemoteClient {

    @GetMapping(value = "/test/getDefaultEchoMsg")
    String getDefaultEchoMsg();

}
