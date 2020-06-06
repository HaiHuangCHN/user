package com.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.dao.entity.UserArch;

@Repository
public interface UserArchRepository extends JpaRepository<UserArch, Integer> {
}