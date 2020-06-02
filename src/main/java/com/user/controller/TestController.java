package com.user.controller;

import java.util.concurrent.ExecutionException;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.user.service.UserService;

import io.swagger.annotations.Api;

@Controller
@RequestMapping(path = "/test")
@Api(tags = "Test API")
public class TestController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @RequestMapping(value = "/archive/data", method = RequestMethod.GET)
    public ResponseEntity<String> archiveData() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
//        userService.archiveData();
//        userService.archiveDataAsync();
        userService.archiveDataAsyncCompletableFuture();
        long end = System.currentTimeMillis();
        logger.info("Total Duration: " + (end - start));
        return ResponseEntity.status(HttpStatus.SC_OK).body("End");
    }

    @RequestMapping(value = "/insert/data", method = RequestMethod.GET)
    public ResponseEntity<Boolean> addUser(@RequestParam Long count) {
        boolean result = userService.insertData(count);
        return ResponseEntity.status(HttpStatus.SC_OK).body(result);
    }

    @RequestMapping(value = "/count/by/email", method = RequestMethod.GET)
    public ResponseEntity<Long> countUser() {
        long result = userService.count();
        return ResponseEntity.status(HttpStatus.SC_OK).body(result);
    }

}
