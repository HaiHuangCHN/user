package com.user.center.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Health API")
public class HealthController {

    @GetMapping(value = "/health")
    @ResponseBody
    public Long checkHealth() {
        return System.currentTimeMillis();
    }

}
