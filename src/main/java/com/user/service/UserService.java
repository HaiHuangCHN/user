package com.user.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.persistence.EntityManager;

import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import com.user.costants.Constants;
import com.user.costants.ErrorCodeEnum;
import com.user.entity.Address;
import com.user.entity.Profile;
import com.user.entity.SexEnum;
import com.user.entity.User;
import com.user.entity.UserArch;
import com.user.exception.ErrorResponseException;
import com.user.exception.InputParameterException;
import com.user.repository.AddressArchRepository;
import com.user.repository.AddressRepository;
import com.user.repository.ProfileArchRepository;
import com.user.repository.ProfileRepository;
import com.user.repository.UserArchRepository;
import com.user.repository.UserDataProvider;
import com.user.repository.UserRepository;
import com.user.request.AddUserReq;
import com.user.request.AddressReq;
import com.user.request.LoginReq;
import com.user.response.AddressResp;
import com.user.response.ProfileInfo;
import com.user.response.ProfileResp;
import com.user.response.TestResp;
import com.user.util.JwtUtils;
import com.user.util.MD5Util;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

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
    EntityManager entityManager;

    @Autowired
    AsyncExecutor asyncExecutor;

    public void validateInboundRequest(Errors errors) throws InputParameterException {
        if (errors.hasErrors()) {
            String errorsMsg = errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).reduce((join, x) -> join + ", " + x).get();
            logger.error("errorCode: " + ErrorCodeEnum.INCOMPLETE_REQUEST_BODY.getSelfDefinedCode());
            throw new InputParameterException(ErrorCodeEnum.INCOMPLETE_REQUEST_BODY.getSelfDefinedCode(), ErrorCodeEnum.INCOMPLETE_REQUEST_BODY.getMessage(), errorsMsg);
        }
    }

    public boolean add(AddUserReq addUserReq) throws ErrorResponseException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String encodePassword = MD5Util.uppercaseMD5(addUserReq.getPassword());
        User user = new User(addUserReq.getUsername(), encodePassword);
        Profile profile = new Profile();
        List<Address> addressList = new ArrayList<>();
        user.setCreatedAt(timestamp);
        user.setUpdatedAt(timestamp);
        user.setProfile(profile);
        profile.setUser(user);
        profile.setSex(SexEnum.valueOf(addUserReq.getNewProfileReq().getSex()));
        profile.setEmail(addUserReq.getNewProfileReq().getEmail());
        profile.setPhoneNum(addUserReq.getNewProfileReq().getPhoneNum());
        profile.setCreatedAt(timestamp);
        profile.setUpdatedAt(timestamp);
//      profileRepository.save(profile);// save user object is enough
        if (addUserReq.getNewProfileReq().getAddressReqList() != null) {
            for (AddressReq address : addUserReq.getNewProfileReq().getAddressReqList()) {
                Address addressDb = new Address();
                addressDb.setProfile(profile);
                addressDb.setCountry(address.getCountry());
                addressDb.setProvience(address.getProvience());
                addressDb.setCity(address.getCity());
                addressDb.setDistrict(address.getDistrict());
                addressDb.setDetailAddress(address.getDetailAddress());
                addressDb.setPostcode(address.getPostcode());
                addressDb.setCreatedAt(timestamp);
                addressDb.setUpdatedAt(timestamp);
//			addressRepository.save(addressDb);// save user object is enough
                addressList.add(addressDb);
            }
        }
        profile.setAddressList(addressList);
        try {
            userDataProvider.saveOrUpdate(user);
            return true;
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException constraintViolationException = (ConstraintViolationException) e.getCause();
                if (Constants.USERNAME.equals(constraintViolationException.getConstraintName())) {
                    logger.error("username is not unique");
                    throw new ErrorResponseException(ErrorCodeEnum.INVALID_USERNAME.getSelfDefinedCode(), ErrorCodeEnum.INVALID_USERNAME.getMessage(),
                            ErrorCodeEnum.INVALID_USERNAME.getDetail());
                }
            }
        }

        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean login(LoginReq loginReq) {
        String encodePassword = MD5Util.uppercaseMD5(loginReq.getPassword());
        User user = userRepository.findByUsernameAndPassword(loginReq.getUsername(), encodePassword);
        Boolean isSuccessful = false;
        if (user != null) {
            isSuccessful = true;
        }
        return isSuccessful;
    }

    public ProfileResp displayProfile(String username) {
        ProfileResp profileResp = new ProfileResp();

        User user = userRepository.findByUsername(username);
        Profile profileDb = user.getProfile();
        Collection<Address> addressList = profileDb.getAddressList();
//        logger.info(profileDb.toString());
//        logger.info(((List<Address>) addressList).get(1).getDetailAddress());

        ProfileInfo profileInfo = new ProfileInfo();
        profileInfo.setEmail(profileDb.getEmail());
        profileInfo.setPhoneNum(profileDb.getPhoneNum());
        profileInfo.setSex(profileDb.getSex().toString());
        profileResp.setProfileInfo(profileInfo);

        List<AddressResp> addressRespList = new ArrayList<>();
        // Usage of Polymorphism, so that super-class object has the data field of the
        // sub-class object
        TestResp testResp = new TestResp();
        testResp.setField1("field1");
        testResp.setField2("field2");
        addressRespList.add(testResp);

        for (Address tmpAddress : addressList) {
            AddressResp addressResp = new AddressResp();
            addressResp.setProvience(tmpAddress.getProvience());
            addressResp.setCountry(tmpAddress.getCountry());
            addressResp.setCity(tmpAddress.getCity());
            addressResp.setDistrict(tmpAddress.getDistrict());
            addressResp.setDetailAddress(tmpAddress.getDetailAddress());
            addressResp.setPostcode(tmpAddress.getPostcode());
            addressRespList.add(addressResp);
        }

        profileResp.setAddressResp(addressRespList);

        String signature = JwtUtils.generateToken(profileInfo);
        profileResp.setSign(signature);
        return profileResp;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public boolean deleteProfile(LoginReq loginReq) throws ErrorResponseException {
        String encodePassword = MD5Util.uppercaseMD5(loginReq.getPassword());
        User user = userRepository.findByUsernameAndPassword(loginReq.getUsername(), encodePassword);
        if (user != null) {
            userRepository.delete(user);
            return true;
        } else {
            throw new ErrorResponseException(ErrorCodeEnum.INVALID_USER.getSelfDefinedCode(), ErrorCodeEnum.INVALID_USER.getMessage(), ErrorCodeEnum.INVALID_USER.getDetail());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public boolean archiveData() {
//        long start = System.currentTimeMillis();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).setMatchingStrategy(MatchingStrategies.STANDARD);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - 3600000);
        List<User> deletedUserList = null;
        do {
//            List<User> deletedUserList = userRepository.deleteByUpdatedAtBefore(timestamp);
//            List<User> deletedUserList = userRepository.findTop1ByUsername("0");
            deletedUserList = userRepository.findTop10000ByUpdatedAtBefore(timestamp);
//            entityManager.clear();
//            long saveStart = System.currentTimeMillis();
            for (int count = 0; count < deletedUserList.size(); count++) {
                UserArch userArch = modelMapper.map(deletedUserList.get(count), UserArch.class);
                userArchRepository.save(userArch);
                entityManager.flush();
                if (count > 0 && count % 100 == 0) {
                    entityManager.clear();
                }
            }
            userRepository.deleteAll(deletedUserList);
        } while (userRepository.findTop10000ByUpdatedAtBefore(timestamp).size() != 0);
//        long saveEnd = System.currentTimeMillis();
//        long end = System.currentTimeMillis();
//        logger.info("Select: " + (saveStart - start));
//        logger.info("Save: " + (saveEnd - saveStart));
//        logger.info("Delete: " + (end - saveEnd));
//        logger.info("Duration: " + (end - start));
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void archiveDataAsync() throws InterruptedException, ExecutionException {
        asyncExecutor.setExecutor(Executors.newFixedThreadPool(10));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - 3600000);
        List<User> deletedTotalUserList = userRepository.findByUpdatedAtBefore(timestamp);
        List<Future<Boolean>> resultList = new LinkedList<>();
        for (int i = 1; i <= deletedTotalUserList.size() / 100; i++) {
            Future<Boolean> result = asyncExecutor.archiveDataAsyc(deletedTotalUserList.subList(((i - 1) * 100), (i * 100)));
            resultList.add(result);
        }
        for (Future<Boolean> r : resultList) {
            while (!r.isDone()) {
                logger.info("Processing......");
                Thread.sleep(10000);
            }
            if (r.isDone()) {
                logger.info(r.get().toString());
            }
        }
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
