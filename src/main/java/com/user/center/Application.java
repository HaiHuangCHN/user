package com.user.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching // or you can add this annotation in configuration class
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}