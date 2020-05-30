package com.user.controller;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.user.costants.Constants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(tags = "Health API")
public class HealthController {

    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    @ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = Constants.SUCCESS, response = String.class),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = Constants.PROFILE_ERROR_RESPONSE) })
    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public @ResponseBody String checkApp() {
        logger.info("OK");
        return "OK";
    }

}
