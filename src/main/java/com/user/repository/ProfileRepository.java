package com.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.dao.entity.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    long countByEmail(String string);

}