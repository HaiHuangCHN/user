package com.user.center.service.job;

import com.user.center.dao.entity.UserDetail;
import com.user.center.dao.entity.UserDetailArch;
import com.user.center.dao.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Service
public class ArchiveRecordTest {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private UserDetailArchRepository userDetailArchRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileArchRepository profileArchRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressArchRepository addressArchRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public boolean archiveDataSingleThread() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).setMatchingStrategy(MatchingStrategies.STANDARD);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - 3600000);
        List<UserDetail> deletedUserDetailList;
        boolean needFurtherProcessed;
        do {
            needFurtherProcessed = false;
            deletedUserDetailList = userDetailRepository.findTop10000ByUpdatedAtBefore(timestamp);
            for (int count = 0; count < deletedUserDetailList.size(); count++) {
                needFurtherProcessed = true;
                UserDetailArch userDetailArch = modelMapper.map(deletedUserDetailList.get(count), UserDetailArch.class);
                userDetailArchRepository.save(userDetailArch);
                userDetailRepository.delete(deletedUserDetailList.get(count));
                entityManager.flush();
            }
            entityManager.clear();
        } while (needFurtherProcessed);

        return true;
    }


}
