package com.user.center.service.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.user.center.dao.entity.UserDetail;
import com.user.center.dao.entity.UserDetailArch;
import com.user.center.service.AsyncExecutor;
import com.user.center.service.IUserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.user.center.dao.repository.UserArchRepository;
import com.user.center.dao.repository.UserRepository;

@Service
public class AsyncExecutorImpl implements AsyncExecutor {

    private static final Logger logger = LoggerFactory.getLogger(IUserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserArchRepository userArchRepository;

    @Autowired
    private EntityManager entityManager;

//    private final ExecutorService executor;

    public AsyncExecutorImpl() {
    }

//    protected ExecutorService getExecutor() {
//        return executor;
//    }

//    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class) // TODO How to set up transaction when run in asynchronous?
//    public void archiveDataAsyncFuture(List<User> deletedUserList, ModelMapper modelMapper) {
//        executor.submit((Callable) () -> {
//            for (int count = 0; count < deletedUserList.size(); count++) {
//                User user = deletedUserList.get(count);
//                UserArch userArch = modelMapper.map(user, UserArch.class);
//                userArchRepository.save(userArch);
//                userArchRepository.flush();
//                userRepository.delete(user);
//                userRepository.flush();
////                // TODO why not able to achieve this
////                // TODO error detail: No EntityManager with actual transaction available for
////                // current thread
////                entityManager.flush();
////                // Test code
////                if (user.getUserId() == 29997) {
////                    throw new Exception("Test");
////                }
//            }
//            entityManager.clear();
//            return "Success";
//        });
//    }
//
//  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class) // TODO How to set up transaction when run in asynchronous?
//    public void archiveDataAsyncCompletableFuture(List<User> deletedUserList, ModelMapper modelMapper) {
//        try {
//            CompletableFuture.supplyAsync(() -> {
//                for (int count = 0; count < deletedUserList.size(); count++) {
//                    User user = deletedUserList.get(count);
//                    UserArch userArch = modelMapper.map(user, UserArch.class);
//                    userArchRepository.save(userArch);
//                    userArchRepository.flush();
//                    userRepository.delete(deletedUserList.get(count));
//                    userRepository.flush();
//////                // Test code
//////                if (user.getUserId() == 29997) {
//////                    throw new Exception("Test");
//////                }
//                }
//                entityManager.clear();
//                return true;
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
//

//    @Async("threadPoolTaskExecutor") // If specify the name
    @Async
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
    public void archiveDataAsync(List<UserDetail> deletedUserDetailList, ModelMapper modelMapper) {
        for (int count = 0; count < deletedUserDetailList.size(); count++) {
            UserDetail userDetail = deletedUserDetailList.get(count);
            UserDetailArch userDetailArch = modelMapper.map(userDetail, UserDetailArch.class);
            userArchRepository.save(userDetailArch);
            userRepository.delete(userDetail);
            entityManager.flush();
//            // Test code
//            if (user.getUserId() == 7) {
//                throw new Exception("Test");
//            }
        }
        entityManager.clear();
        logger.info("Thread: " + Thread.currentThread().getName());
    }

}
