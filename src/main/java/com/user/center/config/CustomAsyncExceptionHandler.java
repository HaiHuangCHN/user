package com.user.center.config;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAsyncExceptionHandler.class);

    @Override
    public void handleUncaughtException(final Throwable throwable, final Method method, final Object... obj) {
        logger.error("Exception message - " + throwable.getMessage());
        logger.error("Method name - " + method.getName());
        for (final Object param : obj) {
            logger.error("Param - " + param);
        }
    }

}
