package com.user.center.service;

import java.util.List;

import com.user.center.dao.entity.UserDetail;
import org.modelmapper.ModelMapper;

public interface AsyncExecutor {

    void archiveDataAsync(List<UserDetail> deletedUserDetailList, ModelMapper modelMapper) throws Exception;

}
