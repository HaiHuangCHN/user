package com.user.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.user.dao.entity.User;
import com.user.dao.entity.UserArch;
import com.user.repository.UserArchRepository;
import com.user.repository.UserRepository;

@Service
public class AsyncExecutor {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserArchRepository userArchRepository;

    @Autowired
    private EntityManager entityManager;

    private ExecutorService executor;

    public AsyncExecutor() {
    }

    public AsyncExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    @Transactional // TODO how set up transaction?
    public Future<Boolean> archiveDataAsyc(List<User> deletedUserList, ModelMapper modelMapper) {
        return executor.submit(() -> {
            for (int count = 0; count < deletedUserList.size(); count++) {
                UserArch userArch = modelMapper.map(deletedUserList.get(count), UserArch.class);
                userArchRepository.save(userArch);
                userArchRepository.flush();
                userRepository.delete(deletedUserList.get(count));
                userRepository.flush();
//                // TODO why not able to achieve this
//                // TODO error detail: No EntityManager with actual transaction available for
//                // current thread
//                entityManager.flush();
            }
            entityManager.clear();
            return true;
        });
    }
}
