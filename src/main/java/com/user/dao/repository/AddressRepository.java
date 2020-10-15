package com.user.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.dao.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}