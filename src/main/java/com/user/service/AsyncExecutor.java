package com.user.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.user.entity.User;
import com.user.entity.UserArch;
import com.user.repository.UserArchRepository;
import com.user.repository.UserRepository;

@Service
public class AsyncExecutor {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserArchRepository userArchRepository;

    @Autowired
    private EntityManager entityManager;

    // TODO why final???
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

    public Future<Boolean> archiveDataAsyc(List<User> deletedUserList) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).setMatchingStrategy(MatchingStrategies.STANDARD);

        return executor.submit(() -> {
            for (int count = 0; count < deletedUserList.size(); count++) {
                UserArch userArch = modelMapper.map(deletedUserList.get(count), UserArch.class);
                userArchRepository.saveAndFlush(userArch);
                if (count > 0 && count % 100 == 0) {
                    entityManager.clear();
                }
            }
            userRepository.deleteAll(deletedUserList);
            userRepository.flush();
            return true;
        });
    }
}
