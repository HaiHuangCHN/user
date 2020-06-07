package com.user.service.job;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.user.dao.entity.User;
import com.user.dao.entity.UserArch;
import com.user.repository.AddressArchRepository;
import com.user.repository.AddressRepository;
import com.user.repository.ProfileArchRepository;
import com.user.repository.ProfileRepository;
import com.user.repository.UserArchRepository;
import com.user.repository.UserDataProvider;
import com.user.repository.UserRepository;
import com.user.service.AsyncExecutor;

@Service
public class ArchiveDatabaseJob {

    private static final Logger logger = LoggerFactory.getLogger(ArchiveDatabaseJob.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserArchRepository userArchRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileArchRepository profileArchRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressArchRepository addressArchRepository;

    @Autowired
    private UserDataProvider userDataProvider;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AsyncExecutor asyncExecutor;

    /**
     * Single thread implementation
     * 
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class)
    public boolean archiveData() {
//        long start = System.currentTimeMillis();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).setMatchingStrategy(MatchingStrategies.STANDARD);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - 3600000);
        List<User> deletedUserList = null;
        boolean needFurtherProcessed = false;
        do {
//            List<User> deletedUserList = userRepository.deleteByUpdatedAtBefore(timestamp);
//            List<User> deletedUserList = userRepository.findTop1ByUsername("0");
            needFurtherProcessed = false;
            deletedUserList = userRepository.findTop10000ByUpdatedAtBefore(timestamp);
//            entityManager.clear();
//            long saveStart = System.currentTimeMillis();
            for (int count = 0; count < deletedUserList.size(); count++) {
                needFurtherProcessed = true;
                UserArch userArch = modelMapper.map(deletedUserList.get(count), UserArch.class);
                userArchRepository.save(userArch);
                userRepository.delete(deletedUserList.get(count));
                entityManager.flush();
            }
            entityManager.clear();
        } while (needFurtherProcessed);
//        long saveEnd = System.currentTimeMillis();
//        long end = System.currentTimeMillis();
//        logger.info("Select: " + (saveStart - start));
//        logger.info("Save: " + (saveEnd - saveStart));
//        logger.info("Delete: " + (end - saveEnd));
//        logger.info("Duration: " + (end - start));
        return true;
    }

    /**
     * Multi thread implementation
     * 
     * @throws Exception
     */
    public void archiveDataAsyncFutureSpring() throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).setMatchingStrategy(MatchingStrategies.STANDARD);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - 0);
        List<User> deletedTotalUserList = userRepository.findByUpdatedAtBefore(timestamp);
        for (int i = 1; i <= deletedTotalUserList.size() / 100; i++) {
            asyncExecutor.archiveDataAsync(deletedTotalUserList.subList(((i - 1) * 100), (i * 100)), modelMapper);
        }
    }

//    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class) // TODO actually no meanning at all
//    public void archiveDataAsyncFuture() throws InterruptedException, ExecutionException {
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).setMatchingStrategy(MatchingStrategies.STANDARD);
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - 0);
//        List<User> deletedTotalUserList = userRepository.findByUpdatedAtBefore(timestamp);
//        List<Future<Boolean>> resultList = new LinkedList<>();
//        for (int i = 1; i <= deletedTotalUserList.size() / 100; i++) {
//            // TODO how to handle when exception?
//            Future<Boolean> result = asyncExecutor.archiveDataAsyncFuture(deletedTotalUserList.subList(((i - 1) * 100), (i * 100)), modelMapper);
//            resultList.add(result);
//        }
////        logger.info(String.valueOf(resultList.size()));
//        for (Future<Boolean> r : resultList) {
//            while (!r.isDone()) {
//                logger.info("Processing......");
//                Thread.sleep(10000);
//            }
////          r.get(); // TODO block logger.info("Processing......");?
//            if (r.isDone()) {
//                r.get();
//            }
//        }
//    }
//
//
//    /**
//     * TODO how to implements asynchronous computation using Executors
//     * 
//     * @throws InterruptedException
//     * @throws ExecutionException
//     */
//    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = Exception.class) // TODO actually no meanning at all
//    public void archiveDataAsyncCompletableFuture() throws InterruptedException, ExecutionException {
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).setMatchingStrategy(MatchingStrategies.STANDARD);
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - 0);
//        List<Future<Boolean>> resultList = new LinkedList<>();
//        List<User> deletedTotalUserList = userRepository.findByUpdatedAtBefore(timestamp);
//        for (int i = 1; i <= deletedTotalUserList.size() / 100; i++) {
//            Future<Boolean> result = asyncExecutor.archiveDataAsyncCompletableFuture(deletedTotalUserList.subList(((i - 1) * 100), (i * 100)), modelMapper);
//            resultList.add(result);
//        }
//        logger.info(String.valueOf(resultList.size()));
//        CompletableFuture.allOf(resultList.toArray(new CompletableFuture[resultList.size()]));
//        for (Future<Boolean> r : resultList) {
//            while (!r.isDone()) {
//                logger.info("Processing......");
//                Thread.sleep(10000);
//            }
//            if (r.isDone()) {
//                r.get();
//            }
//        }
//    }

}
