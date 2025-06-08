package com.user.center.controller;

import com.user.center.dto.req.LoginReq;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api(tags = "Common API")
@Slf4j
@Controller
public class LoginController {

    /**
     * @param request
     * @param session
     * @param loginReq
     * @return
     */
    @GetMapping(value = "/login/page")
    public String loginPage(HttpServletRequest request, HttpSession session, LoginReq loginReq) {
        log.info("{}, method: {}{}", request.getRequestURL().toString(), request.getMethod(), loginReq.toString());
        return "login";
    }

}
