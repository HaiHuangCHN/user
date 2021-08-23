package com.user.center.service;

import com.user.center.dto.response.ProfileResp;
import com.user.center.exception.BusinessException;
import com.user.center.exception.InputParameterException;
import com.user.center.dto.request.AddUserDetailReq;
import com.user.center.dto.request.LoginReq;
import org.springframework.retry.annotation.Retryable;
import org.springframework.validation.Errors;

public interface IUserService {

    void validateInboundRequest(Errors errors) throws InputParameterException;

    @Retryable(value = RuntimeException.class)
    boolean add(AddUserDetailReq addUserDetailReq) throws BusinessException;

    Boolean login(LoginReq loginReq);

    ProfileResp displayProfile(String username);

    boolean deleteProfile(LoginReq loginReq) throws BusinessException;

}
