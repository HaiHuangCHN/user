package com.user.center.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Health API")
public class HealthController {

    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    @ResponseBody
    public Long checkHealth() {
        return System.currentTimeMillis();
    }

}
