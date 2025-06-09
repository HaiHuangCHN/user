package com.user.center.service.impl;

import com.user.center.costant.ErrorCodeEnum;
import com.user.center.dao.entity.Address;
import com.user.center.dao.entity.Profile;
import com.user.center.dao.entity.SexEnum;
import com.user.center.dao.entity.UserDetail;
import com.user.center.dao.repository.AddressRepository;
import com.user.center.dao.repository.ProfileRepository;
import com.user.center.dao.repository.UserDataProvider;
import com.user.center.dao.repository.UserDetailRepository;
import com.user.center.dto.req.CreateAddressReqVO;
import com.user.center.dto.req.CreateUserDetailReqVO;
import com.user.center.dto.req.LoginReq;
import com.user.center.dto.res.AddressResVO;
import com.user.center.dto.res.ProfileInfoResVO;
import com.user.center.dto.res.ProfileResVO;
import com.user.center.dto.res.TestResVO;
import com.user.center.exception.BusinessException;
import com.user.center.service.IUserService;
import com.user.center.util.JwtUtils;
import com.user.center.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * UserService Implementation
 *
 * @author hai.huang.a@outlook.com
 * @date 2021-08-01 15:25:35
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserDataProvider userDataProvider;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void validateInboundRequest(Errors errors) {
        if (errors.hasErrors()) {
            String errorsMsg = errors.getAllErrors().stream()
                    .map(x -> x.getDefaultMessage())
                    .reduce((join, x) -> join + ", " + x).get();
            log.error("errorCode: {}", ErrorCodeEnum.INCOMPLETE_REQUEST_BODY.getSelfDefinedCode());
            throw new BusinessException(ErrorCodeEnum.INCOMPLETE_REQUEST_BODY.getSelfDefinedCode(),
                    ErrorCodeEnum.INCOMPLETE_REQUEST_BODY.getMessage(), errorsMsg);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public boolean createUser(CreateUserDetailReqVO createUserDetailReqVO) throws BusinessException {
//        // Test transaction
//        log.info("Calling...");
//        throw new BusinessException("001", "Test Retry", "Test Retry");
        LocalDateTime currentDatetime = LocalDateTime.now();
        String encodePassword = MD5Util.uppercaseMD5(createUserDetailReqVO.getPassword());
        UserDetail userDetail = new UserDetail(createUserDetailReqVO.getUsername(), encodePassword);
        userDetail.setYn(true);
        userDetail.setCreatedBy("system");
        userDetail.setCreatedAt(currentDatetime);
        userDetail.setUpdatedBy("system");
        userDetail.setUpdatedAt(currentDatetime);
        userDetailRepository.save(userDetail);
        Profile profileDb = new Profile();
        profileDb.setUserDetail(userDetail);
        profileDb.setSex(SexEnum.valueOf(createUserDetailReqVO.getProfile().getSex()));
        profileDb.setEmail(createUserDetailReqVO.getProfile().getEmail());
        profileDb.setPhoneNum(createUserDetailReqVO.getProfile().getPhoneNum());
        profileDb.setYn(true);
        profileDb.setCreatedBy("system");
        profileDb.setCreatedAt(currentDatetime);
        profileDb.setUpdatedBy("system");
        profileDb.setUpdatedAt(currentDatetime);
        // Save user object is enough, if Cascade is enable
        profileRepository.save(profileDb);
        if (createUserDetailReqVO.getProfile().getAddresses() != null) {
            for (CreateAddressReqVO address : createUserDetailReqVO.getProfile().getAddresses()) {
                Address addressDb = this.buildAddress(address, profileDb, currentDatetime);
                addressRepository.save(addressDb);// Save user object is enough, if Cascade is enable
            }
        }
        return true;
    }

    private Address buildAddress(CreateAddressReqVO addressCreate, Profile profile, LocalDateTime currentDatetime) {
        Address addressDb = new Address();
        addressDb.setProfile(profile);
        addressDb.setCountry(addressCreate.getCountry());
        addressDb.setProvience(addressCreate.getProvince());
        addressDb.setCity(addressCreate.getCity());
        addressDb.setDistrict(addressCreate.getDistrict());
        addressDb.setDetailAddress(addressCreate.getDetailAddress());
        addressDb.setPostcode(addressCreate.getPostcode());
        addressDb.setYn(true);
        addressDb.setCreatedBy("system");
        addressDb.setCreatedAt(currentDatetime);
        addressDb.setUpdatedBy("system");
        addressDb.setUpdatedAt(currentDatetime);
        return addressDb;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean login(LoginReq loginReq) {
        String encodePassword = MD5Util.uppercaseMD5(loginReq.getPassword());
        UserDetail userDetail = userDetailRepository.findByUsernameAndPassword(loginReq.getUsername(), encodePassword);
        boolean isSuccessful = false;
        if (userDetail != null) {
            isSuccessful = true;
        }
        return isSuccessful;
    }

    @Override
    public ProfileResVO displayProfile(String username) {
        ProfileResVO profileResVo = new ProfileResVO();

        UserDetail userDetail = userDataProvider.findByUsername(username);
        Profile profileDb = userDetail.getProfile();
        List<Address> addressDbList = profileDb.getAddressList();

        ProfileInfoResVO profileInfoResVO = new ProfileInfoResVO();
        profileInfoResVO.setEmail(profileDb.getEmail());
        profileInfoResVO.setPhoneNum(profileDb.getPhoneNum());
        profileInfoResVO.setSex(profileDb.getSex().toString());
        profileResVo.setProfile(profileInfoResVO);

        List<AddressResVO> addresses = new ArrayList<>();
        // Usage of Polymorphism, so that super-class object has the data field of the sub-class object
        TestResVO testResp = new TestResVO();
        testResp.setField1("field1");
        testResp.setField2("field2");
        addresses.add(testResp);

        for (Address addressDb : addressDbList) {
            AddressResVO address = new AddressResVO();
            address.setProvince(addressDb.getProvience());
            address.setCountry(addressDb.getCountry());
            address.setCity(addressDb.getCity());
            address.setDistrict(addressDb.getDistrict());
            address.setDetailAddress(addressDb.getDetailAddress());
            address.setPostcode(addressDb.getPostcode());
            addresses.add(address);
        }

        profileResVo.setAddresses(addresses);

        String signature = JwtUtils.generateToken(profileInfoResVO);
        profileResVo.setSign(signature);
        return profileResVo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public boolean deleteProfile(LoginReq loginReq) throws BusinessException {
        String encodePassword = MD5Util.uppercaseMD5(loginReq.getPassword());
        UserDetail userDetail = userDetailRepository.findByUsernameAndPassword(loginReq.getUsername(), encodePassword);
        if (userDetail != null) {
            userDetailRepository.delete(userDetail);
            return true;
        } else {
            throw new BusinessException(
                    ErrorCodeEnum.INVALID_USER.getSelfDefinedCode(),
                    ErrorCodeEnum.INVALID_USER.getMessage(),
                    ErrorCodeEnum.INVALID_USER.getDetail()
            );
        }
    }

}
