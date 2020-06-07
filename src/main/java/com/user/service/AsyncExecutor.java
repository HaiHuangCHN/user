package com.user.service;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import javax.persistence.EntityManager;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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

    // TODO change it into final
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

//    @Async
//    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class) // TODO how to set up transaction?
//    public void archiveDataAsycFuture(List<User> deletedUserList, ModelMapper modelMapper) {
//        try {
//            executor.submit((Callable) () -> {
//                for (int count = 0; count < deletedUserList.size(); count++) {
//                    User user = deletedUserList.get(count);
//                    UserArch userArch = modelMapper.map(user, UserArch.class);
//                    userArchRepository.save(userArch);
//                    userArchRepository.flush();
//                    userRepository.delete(user);
//                    userRepository.flush();
////                // TODO why not able to achieve this
////                // TODO error detail: No EntityManager with actual transaction available for
////                // current thread
////                entityManager.flush();
//                    if (user.getUserId() == 29997) {
//                        throw new Exception("Test");
//                    }
//                }
//                entityManager.clear();
//                return "End";
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
//
//    @Async
//    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class) // TODO how to set up transaction?
//    public void archiveDataAsycCompletableFuture(List<User> deletedUserList, ModelMapper modelMapper) {
//        try {
//            CompletableFuture.supplyAsync(() -> {
//                for (int count = 0; count < deletedUserList.size(); count++) {
//                    User user = deletedUserList.get(count);
//                    UserArch userArch = modelMapper.map(user, UserArch.class);
//                    userArchRepository.save(userArch);
//                    userArchRepository.flush();
//                    userRepository.delete(deletedUserList.get(count));
//                    userRepository.flush();
//                    if (user.getUserId() == 29997) {
//                        throw new RuntimeException("Test");
//                    }
//                }
//                entityManager.clear();
//                return true;
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }

    @Async
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class) // TODO how to set up transaction?
    public void archiveDataAsyc(List<User> deletedUserList, ModelMapper modelMapper) throws Exception {
        for (int count = 0; count < deletedUserList.size(); count++) {
            User user = deletedUserList.get(count);
            UserArch userArch = modelMapper.map(user, UserArch.class);
            userArchRepository.save(userArch);
            userArchRepository.flush();
            userRepository.delete(user);
            userRepository.flush();
            if (user.getUserId() == 29997) {
                throw new Exception("Test");
            }
        }
        entityManager.clear();
    }

}
