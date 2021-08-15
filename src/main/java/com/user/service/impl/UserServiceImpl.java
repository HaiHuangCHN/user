package com.user.service.impl;

import com.user.costant.ErrorCodeEnum;
import com.user.dao.entity.Address;
import com.user.dao.entity.Profile;
import com.user.dao.entity.User;
import com.user.dao.repository.*;
import com.user.domain.dto.request.AddUserReq;
import com.user.domain.dto.request.LoginReq;
import com.user.domain.dto.response.AddressResp;
import com.user.domain.dto.response.ProfileInfo;
import com.user.domain.dto.response.ProfileResp;
import com.user.domain.dto.response.TestResp;
import com.user.exception.BusinessException;
import com.user.exception.InputParameterException;
import com.user.service.IUserService;
import com.user.util.JwtUtils;
import com.user.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * UserService Implementation
 *
 * @author hai.huang.a@outlook.com
 * @date 2021-08-01 15:25:35
 */
@Service
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(IUserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileArchRepository profileArchRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserDataProvider userDataProvider;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void validateInboundRequest(Errors errors) throws InputParameterException {
        if (errors.hasErrors()) {
            String errorsMsg = errors.getAllErrors().stream().map(x -> x.getDefaultMessage()).reduce((join, x) -> join + ", " + x).get();
            logger.error("errorCode: " + ErrorCodeEnum.INCOMPLETE_REQUEST_BODY.getSelfDefinedCode());
            throw new InputParameterException(ErrorCodeEnum.INCOMPLETE_REQUEST_BODY.getSelfDefinedCode(), ErrorCodeEnum.INCOMPLETE_REQUEST_BODY.getMessage(), errorsMsg);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public boolean add(AddUserReq addUserReq) throws BusinessException {
        logger.info("Calling...");
        throw new BusinessException("001", "Test Retry", "Test Retry");
//        userDataProvider.add(addUserReq);
//        String encodePassword = MD5Util.uppercaseMD5(addUserReq.getPassword());
//        User user = new User(addUserReq.getUsername(), encodePassword);
//        userRepository.save(user);
//        Profile profile = new Profile();
//        profile.setUser(user);
//        profile.setSex(SexEnum.valueOf(addUserReq.getNewProfileReq().getSex()));
//        profile.setEmail(addUserReq.getNewProfileReq().getEmail());
//        profile.setPhoneNum(addUserReq.getNewProfileReq().getPhoneNum());
//        profileRepository.save(profile);// save user object is enough
//        if (addUserReq.getNewProfileReq().getAddressReqList() != null) {
//            for (AddressReq address : addUserReq.getNewProfileReq().getAddressReqList()) {
//                Address addressDb = new Address();
//                addressDb.setProfile(profile);
//                addressDb.setCountry(address.getCountry());
//                addressDb.setProvience(address.getProvience());
//                addressDb.setCity(address.getCity());
//                addressDb.setDistrict(address.getDistrict());
//                addressDb.setDetailAddress(address.getDetailAddress());
//                addressDb.setPostcode(address.getPostcode());
//                addressRepository.save(addressDb);// save user object is enough
//            }
//        }
//        return true;
    }

    @Override
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

    @Override
    public ProfileResp displayProfile(String username) {
        ProfileResp profileResp = new ProfileResp();

        User user = userDataProvider.findByUsername(username);
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public boolean deleteProfile(LoginReq loginReq) throws BusinessException {
        String encodePassword = MD5Util.uppercaseMD5(loginReq.getPassword());
        User user = userRepository.findByUsernameAndPassword(loginReq.getUsername(), encodePassword);
        if (user != null) {
            userRepository.delete(user);
            return true;
        } else {
            throw new BusinessException(ErrorCodeEnum.INVALID_USER.getSelfDefinedCode(), ErrorCodeEnum.INVALID_USER.getMessage(), ErrorCodeEnum.INVALID_USER.getDetail());
        }
    }

}
