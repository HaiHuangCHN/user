package com.user.center.dao.repository;

import com.user.center.dao.entity.AddressArch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressArchRepository extends JpaRepository<AddressArch, Long> {
}