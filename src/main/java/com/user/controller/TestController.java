package com.user.controller;

import com.user.dao.entity.Address;
import com.user.dao.entity.Profile;
import com.user.dao.entity.SexEnum;
import com.user.dao.entity.User;
import com.user.dao.repository.*;
import com.user.service.AsyncExecutor;
import com.user.service.job.ArchiveRecordTest;
import io.swagger.annotations.Api;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/test")
@Api(tags = "Test API")
public class TestController {

    private static final Logger logger = LogManager.getLogger(TestController.class);

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

    @Autowired
    private ArchiveRecordTest archiveRecordTest;

    @RequestMapping(value = "/archive/data", method = RequestMethod.GET)
    public ResponseEntity<String> archiveData() throws Exception {
        long start = System.currentTimeMillis();
        archiveRecordTest.archiveData();
//        archiveDatabaseJob.archiveDataAsyncFuture();
//        archiveDatabaseJob.archiveDataAsyncCompletableFuture();
//      archiveDatabaseJob.archiveDataAsyncFutureSpring();
        long end = System.currentTimeMillis();
        logger.info("Total Duration: " + (end - start));
        return ResponseEntity.status(HttpStatus.SC_OK).body("End");
    }

    @RequestMapping(value = "/insert/data", method = RequestMethod.GET)
    public ResponseEntity<Boolean> addUser(@RequestParam Long count) {
        boolean result = insertData(count);
        return ResponseEntity.status(HttpStatus.SC_OK).body(result);
    }

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

}
