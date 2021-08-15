package com.user.controller;

import com.user.costant.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Health API")
public class HealthController {

    private static final Logger logger = LoggerFactory.getLogger(HealthController.class);

    // Please take care: @Value not able to inject into static field
    @Value("${test.test:1}")
    public static final Integer testTest = 0;

    // Please take care: @Value able to inject into final field
    @Value("${test.test.test:1}")
    private Integer TEST_TEST_TEST;

    @ApiResponses(value = {@ApiResponse(code = HttpStatus.SC_OK, message = Constants.SUCCESS, response = String.class),
            @ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = Constants.ERROR_RESPONSE)})
    @RequestMapping(value = "/health", method = RequestMethod.GET)
    @ResponseBody
    public String checkApp() {

//        // Test stub
//        // TODO No major diff when print on console, but how about print into a file or in other ways?
//        Exception e = new Exception("test");
//        logger.error(e.getMessage(), e);
//        logger.info("-----------------------VS---------------------------");
//        logger.error(ExceptionUtils.toString(e));

        // Test stub
        logger.info(testTest.toString());

        // Test stub
        logger.info(TEST_TEST_TEST.toString());

        logger.info("OK");
        return "OK";
    }

}
