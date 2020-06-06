package com.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.user.dto.request.LoginReq;

import io.swagger.annotations.Api;

@Controller
@RequestMapping(path = "/common")
@Api(tags = "Common API")
public class CommonController {

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @RequestMapping(value = "/login/page", method = RequestMethod.GET)
    public String loginPage(HttpServletRequest request, HttpSession session, LoginReq loginReq) {
        logger.info(request.getRequestURL().toString() + ", method: " + request.getMethod() + loginReq.toString());
        return "login";
    }

}
