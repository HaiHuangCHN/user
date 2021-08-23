package com.user.center.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.center.dto.request.LoginReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/redis")
@Api(tags = "Redis API")
public class RedisController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PutMapping("set/string/{key}/{value}")
    @ApiOperation(value = "String: set cache")
    public String setString(@PathVariable("key") String key, @PathVariable("value") String value) {
        // Caution: key can not be null as Spring won't allow
        stringRedisTemplate.opsForValue().set(key, value);
        return key + ", " + value;
    }

    @GetMapping("get/string/{key}")
    @ApiOperation(value = "String: get cache by key")
    public String getString(@PathVariable("key") String key) {
        return "key=" + key + ", value=" + stringRedisTemplate.opsForValue().get(key);
    }

    @PostMapping("set/object/{key}")
    @ApiOperation(value = "Object: set cache")
    public String setObject(@PathVariable("key") String key, @RequestBody LoginReq loginReq) {
        // Caution: key can not be null as Spring won't allow
        redisTemplate.opsForValue().set(key, loginReq);
        return key + ", " + loginReq;
    }

    @GetMapping("get/object/{key}")
    @ApiOperation(value = "Obejct: get cache by key")
    public String getObject(@PathVariable("key") String key) {
        return "key=" + key + ", value=" + redisTemplate.opsForValue().get(key);
    }

    @PostMapping("set/object/expiration/{key}/{timeOut}")
    @ApiOperation(value = "Object: set expiration cache")
    public String setObjectExpr(@PathVariable("key") String key, @PathVariable("timeOut") long timeOut, @RequestBody LoginReq loginReq) {
        redisTemplate.opsForValue().set(key, loginReq, timeOut, TimeUnit.SECONDS);
        return key + ", " + loginReq;
    }

    @DeleteMapping("delete/{key}")
    @ApiOperation(value = "Object: delete cache")
    public String deleteCache(@PathVariable("key") String key) {
        redisTemplate.delete(key);
        return key + ", " + redisTemplate.opsForValue().get(key);
    }

}
