package com.user.service;

import com.user.domain.dto.request.AddUserReq;
import com.user.domain.dto.request.LoginReq;
import com.user.domain.dto.response.ProfileResp;
import com.user.exception.BusinessException;
import com.user.exception.InputParameterException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.validation.Errors;

public interface IUserService {

    void validateInboundRequest(Errors errors) throws InputParameterException;

    @Retryable(value = RuntimeException.class)
    boolean add(AddUserReq addUserReq) throws BusinessException;

    Boolean login(LoginReq loginReq);

    ProfileResp displayProfile(String username);

    boolean deleteProfile(LoginReq loginReq) throws BusinessException;

}
