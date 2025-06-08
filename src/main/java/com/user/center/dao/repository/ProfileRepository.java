package com.user.center.dao.repository;

import com.user.center.dao.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    long countByEmail(String string);

}