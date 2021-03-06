package com.user.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.dao.entity.ProfileArch;

@Repository
public interface ProfileArchRepository extends JpaRepository<ProfileArch, Integer> {
}