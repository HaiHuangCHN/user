package com.user.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.persistence.EntityManager;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.user.dao.entity.Address;
import com.user.dao.entity.Profile;
import com.user.dao.entity.SexEnum;
import com.user.dao.entity.User;
import com.user.repository.AddressArchRepository;
import com.user.repository.AddressRepository;
import com.user.repository.ProfileArchRepository;
import com.user.repository.ProfileRepository;
import com.user.repository.UserArchRepository;
import com.user.repository.UserDataProvider;
import com.user.repository.UserRepository;
//import com.user.service.AsyncExecutor;
import com.user.service.job.ArchiveDatabaseJob;

import io.swagger.annotations.Api;

@Controller
@RequestMapping(path = "/test")
@Api(tags = "Test API")
public class TestController {
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

//    @Autowired
//    private AsyncExecutor asyncExecutor;

    @Autowired
    private ArchiveDatabaseJob archiveDatabaseJob;

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @RequestMapping(value = "/archive/data", method = RequestMethod.GET)
    public ResponseEntity<String> archiveData() throws Exception {
        long start = System.currentTimeMillis();
//        archiveDatabaseJob.archiveData();
//        archiveDatabaseJob.archiveDataAsyncFuture();
//        archiveDatabaseJob.archiveDataAsyncCompletableFuture();
      archiveDatabaseJob.archiveDataAsyncFutureSpring();
        long end = System.currentTimeMillis();
        logger.info("Total Duration: " + (end - start));
        return ResponseEntity.status(HttpStatus.SC_OK).body("End");
    }

    @RequestMapping(value = "/insert/data", method = RequestMethod.GET)
    public ResponseEntity<Boolean> addUser(@RequestParam Long count) {
        boolean result = insertData(count);
        return ResponseEntity.status(HttpStatus.SC_OK).body(result);
    }

    @RequestMapping(value = "/count/by/email", method = RequestMethod.GET)
    public ResponseEntity<Long> countUser() {
        long result = count();
        return ResponseEntity.status(HttpStatus.SC_OK).body(result);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public boolean insertData(long count) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - 360000000L);
        for (int i = 200001; i <= count; i++) {
            User user = new User();
            Profile profile = new Profile();
            List<Address> addressList = new ArrayList<>();
            user.setUsername(String.valueOf(i));
            user.setPassword("password");
            user.setCreatedAt(timestamp);
            user.setUpdatedAt(timestamp);
            user.setProfile(profile);
            profile.setUser(user);
            profile.setSex(SexEnum.MALE);
            profile.setEmail("test@mail.com");
            profile.setPhoneNum("12345678");
            profile.setCreatedAt(timestamp);
            profile.setUpdatedAt(timestamp);
            Address addressDb = new Address();
            addressDb.setProfile(profile);
            addressDb.setCountry("test");
            addressDb.setProvience("test");
            addressDb.setCity("test");
            addressDb.setDistrict("test");
            addressDb.setDetailAddress("test");
            addressDb.setPostcode("0000");
            addressDb.setCreatedAt(timestamp);
            addressDb.setUpdatedAt(timestamp);
            addressList.add(addressDb);
            profile.setAddressList(addressList);
            userRepository.save(user);
            profileRepository.save(user.getProfile());
            addressRepository.saveAll(user.getProfile().getAddressList());
            if (i % 100 == 0) {
                entityManager.clear();
            }
        }
        return true;
    }

    public long count() {
        long count = profileRepository.countByEmail("test@mail.com");
        return count;
    }

}
