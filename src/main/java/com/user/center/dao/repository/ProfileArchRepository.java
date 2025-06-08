package com.user.center.dao.repository;

import com.user.center.dao.entity.ProfileArch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileArchRepository extends JpaRepository<ProfileArch, Long> {
}