package com.user.service;

import java.util.List;

import org.modelmapper.ModelMapper;

import com.user.dao.entity.User;

public interface AsyncExecutor {

    void archiveDataAsync(List<User> deletedUserList, ModelMapper modelMapper) throws Exception;

}
