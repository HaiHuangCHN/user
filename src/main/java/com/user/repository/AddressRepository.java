package com.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}