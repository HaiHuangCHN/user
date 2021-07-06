package com.user.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.dao.entity.AddressArch;

@Repository
public interface AddressArchRepository extends JpaRepository<AddressArch, Integer> {
}