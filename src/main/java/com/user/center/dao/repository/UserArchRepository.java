package com.user.center.dao.repository;

import com.user.center.dao.entity.UserDetailArch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserArchRepository extends JpaRepository<UserDetailArch, Integer> {
}