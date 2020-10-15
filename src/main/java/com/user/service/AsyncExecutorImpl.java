package com.user.service;

import java.util.List;
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
import com.user.dao.repository.UserArchRepository;
import com.user.dao.repository.UserRepository;

@Service
public class AsyncExecutorImpl implements AsyncExecutor {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

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

//    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class) // TODO how to set up transaction when run in asynchronous?
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
////                // TODO dummy code
////                if (user.getUserId() == 29997) {
////                    throw new Exception("Test");
////                }
//            }
//            entityManager.clear();
//            return "Success";
//        });
//    }
//
//  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class) // TODO how to set up transaction when run in asynchronous?
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
//////                // TODO dummy code
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
//    @Async("threadPoolTaskExecutor")
    @Async
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
    public void archiveDataAsync(List<User> deletedUserList, ModelMapper modelMapper) throws Exception {
        for (int count = 0; count < deletedUserList.size(); count++) {
            User user = deletedUserList.get(count);
            UserArch userArch = modelMapper.map(user, UserArch.class);
            userArchRepository.save(userArch);
            userRepository.delete(user);
            entityManager.flush();
//            // TODO dummy code
//            if (user.getUserId() == 7) {
//                throw new Exception("Test");
//            }
        }
        entityManager.clear();
        logger.info("Thread: " + Thread.currentThread().getName());
    }

}
