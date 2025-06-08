package com.user.center.dao.repository;

import com.user.center.dao.entity.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserDataProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Value("${cache.default.expire-time}")
    private Long defaultExpireTime;

    // Bean name "keyGenerator" should exist, or will cause error
    @Cacheable(value = "users", key = "#username")
    public UserDetail findByUsername(String username) {
        log.info("Enter findByUserName method");
        UserDetail userDetail = userRepository.findByUsername(username);
        return userDetail;
    }

    @CachePut(value = "users", key = "#userDetail.userId")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public UserDetail saveOrUpdate(UserDetail userDetail) {
        log.info("Enter saveOrUpdate method");
        UserDetail userDetailSaved = userRepository.save(userDetail);
        profileRepository.save(userDetail.getProfile());
        addressRepository.saveAll(userDetail.getProfile().getAddressList());
        return userDetailSaved;// TODO What userDetailSaved looks like? How about the id?
    }

    @CacheEvict(value = "users", key = "#userDetail.userId")
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void delete(UserDetail userDetail) throws Exception {
//        // Test Stub
//        log.info("Enter delete method");
//        redisCacheUtil.delete("users::" + userDetail.getUsername());
//        if (6 / 3 == 2) {
//            throw new Exception();
//        }
        userRepository.delete(userDetail);
    }

    // Method to simulate a slow response
    private void simulateSlowService() {
        try {
            long time = 3000L;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

}