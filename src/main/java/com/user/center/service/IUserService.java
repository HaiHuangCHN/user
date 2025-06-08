package com.user.center.service;

import com.user.center.dto.req.CreateUserDetailReqVO;
import com.user.center.dto.req.LoginReq;
import com.user.center.dto.res.ProfileResp;
import com.user.center.exception.BusinessException;
import org.springframework.validation.Errors;

public interface IUserService {

    void validateInboundRequest(Errors errors);

    boolean createUser(CreateUserDetailReqVO createUserDetailReqVO) throws BusinessException;

    Boolean login(LoginReq loginReq);

    ProfileResp displayProfile(String username);

    boolean deleteProfile(LoginReq loginReq) throws BusinessException;

}
