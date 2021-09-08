package com.user.center.controller;

import com.user.center.dao.entity.Address;
import com.user.center.dao.entity.Profile;
import com.user.center.dao.entity.SexEnum;
import com.user.center.dao.entity.UserDetail;
import com.user.center.dao.repository.*;
import com.user.center.service.job.ArchiveRecordTest;
import com.user.center.service.AsyncExecutor;
import io.swagger.annotations.Api;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/test")
@Api(tags = "Test API")
public class TestController {

    private static final Logger logger = LogManager.getLogger(TestController.class);

    // test1
    //  test2
    // test3
    @Value("#{'${test.list}'.split(',')}")
    private List<String> strList;

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
        archiveRecordTest.archiveDataSingleThread();
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
            UserDetail userDetail = new UserDetail();
            Profile profile = new Profile();
            List<Address> addressList = new ArrayList<>();
            userDetail.setUsername(String.valueOf(i));
            userDetail.setPassword("password");
            userDetail.setCreatedAt(LocalDateTime.now());
            userDetail.setUpdatedAt(LocalDateTime.now());
            userDetail.setProfile(profile);
            profile.setUser(userDetail);
            profile.setSex(SexEnum.MALE);
            profile.setEmail("test@mail.com");
            profile.setPhoneNum("12345678");
            profile.setCreatedAt(LocalDateTime.now());
            profile.setUpdatedAt(LocalDateTime.now());
            Address addressDb = new Address();
            addressDb.setProfile(profile);
            addressDb.setCountry("test");
            addressDb.setProvience("test");
            addressDb.setCity("test");
            addressDb.setDistrict("test");
            addressDb.setDetailAddress("test");
            addressDb.setPostcode("0000");
            addressDb.setCreatedAt(LocalDateTime.now());
            addressDb.setUpdatedAt(LocalDateTime.now());
            addressList.add(addressDb);
            profile.setAddressList(addressList);
            userRepository.save(userDetail);
            profileRepository.save(userDetail.getProfile());
            addressRepository.saveAll(userDetail.getProfile().getAddressList());
            if (i % 100 == 0) {
                entityManager.clear();
            }
        }
        return true;
    }

    // Please take care: @Value not able to inject into static field
    @Value("${test.test:1}")
    public static final Integer testTest = 0;

    // Please take care: @Value able to inject into final field
    @Value("${test.test.test:1}")
    private Integer TEST_TEST_TEST;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String test() {

//        // Test stub
//        // TODO No major diff when print on console, but how about print into a file or in other ways?
//        Exception e = new Exception("test");
//        logger.error(e.getMessage(), e);
//        logger.info("-----------------------VS---------------------------");
//        logger.error(ExceptionUtils.toString(e));

        // Test stub
        logger.info(testTest.toString());

        // Test stub
        logger.info(TEST_TEST_TEST.toString());

        // Test stub
        logger.info(strList);
        logger.info(strList.get(0));
        logger.info(strList.get(1));
        logger.info(strList.get(2));

        return "OK";
    }


}
