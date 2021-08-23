package com.user.center.service.impl;

import com.user.center.costant.ErrorCodeEnum;
import com.user.center.dao.entity.Address;
import com.user.center.dao.entity.Profile;
import com.user.center.dao.entity.SexEnum;
import com.user.center.dao.entity.UserDetail;
import com.user.center.dao.repository.*;
import com.user.center.dto.request.AddressReq;
import com.user.center.dto.response.AddressResp;
import com.user.center.dto.response.ProfileInfo;
import com.user.center.dto.response.ProfileResp;
import com.user.center.dto.response.TestResp;
import com.user.center.exception.BusinessException;
import com.user.center.exception.InputParameterException;
import com.user.center.util.JwtUtils;
import com.user.center.dto.request.AddUserDetailReq;
import com.user.center.dto.request.LoginReq;
import com.user.center.service.IUserService;
import com.user.center.util.MD5Util;
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
    public boolean add(AddUserDetailReq addUserDetailReq) throws BusinessException {
//        // Test retry
//        logger.info("Calling...");
//        throw new BusinessException("001", "Test Retry", "Test Retry");
        String encodePassword = MD5Util.uppercaseMD5(addUserDetailReq.getPassword());
        UserDetail userDetail = new UserDetail(addUserDetailReq.getUsername(), encodePassword);
        userRepository.save(userDetail);
        Profile profile = new Profile();
        profile.setUser(userDetail);
        profile.setSex(SexEnum.valueOf(addUserDetailReq.getNewProfileReq().getSex()));
        profile.setEmail(addUserDetailReq.getNewProfileReq().getEmail());
        profile.setPhoneNum(addUserDetailReq.getNewProfileReq().getPhoneNum());
        profileRepository.save(profile);// Save user object is enough, if Cascade is enable
        if (addUserDetailReq.getNewProfileReq().getAddressReqList() != null) {
            for (AddressReq address : addUserDetailReq.getNewProfileReq().getAddressReqList()) {
                Address addressDb = new Address();
                addressDb.setProfile(profile);
                addressDb.setCountry(address.getCountry());
                addressDb.setProvience(address.getProvince());
                addressDb.setCity(address.getCity());
                addressDb.setDistrict(address.getDistrict());
                addressDb.setDetailAddress(address.getDetailAddress());
                addressDb.setPostcode(address.getPostcode());
                addressRepository.save(addressDb);// Save user object is enough, if Cascade is enable
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean login(LoginReq loginReq) {
        String encodePassword = MD5Util.uppercaseMD5(loginReq.getPassword());
        UserDetail userDetail = userRepository.findByUsernameAndPassword(loginReq.getUsername(), encodePassword);
        Boolean isSuccessful = false;
        if (userDetail != null) {
            isSuccessful = true;
        }
        return isSuccessful;
    }

    @Override
    public ProfileResp displayProfile(String username) {
        ProfileResp profileResp = new ProfileResp();

        UserDetail userDetail = userDataProvider.findByUsername(username);
        Profile profileDb = userDetail.getProfile();
        Collection<Address> addressList = profileDb.getAddressList();

        ProfileInfo profileInfo = new ProfileInfo();
        profileInfo.setEmail(profileDb.getEmail());
        profileInfo.setPhoneNum(profileDb.getPhoneNum());
        profileInfo.setSex(profileDb.getSex().toString());
        profileResp.setProfileInfo(profileInfo);

        List<AddressResp> addressRespList = new ArrayList<>();
        // Usage of Polymorphism, so that super-class object has the data field of the sub-class object
        TestResp testResp = new TestResp();
        testResp.setField1("field1");
        testResp.setField2("field2");
        addressRespList.add(testResp);

        for (Address tmpAddress : addressList) {
            AddressResp addressResp = new AddressResp();
            addressResp.setProvince(tmpAddress.getProvience());
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
        UserDetail userDetail = userRepository.findByUsernameAndPassword(loginReq.getUsername(), encodePassword);
        if (userDetail != null) {
            userRepository.delete(userDetail);
            return true;
        } else {
            throw new BusinessException(ErrorCodeEnum.INVALID_USER.getSelfDefinedCode(), ErrorCodeEnum.INVALID_USER.getMessage(), ErrorCodeEnum.INVALID_USER.getDetail());
        }
    }

}
