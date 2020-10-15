package com.user.dao.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.dao.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from user.user where username=:username and password=:password", nativeQuery = true)
    public User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    public User findByUsername(String username);

    public List<User> deleteByUpdatedAtBefore(Timestamp updatedAt);

    public List<User> findByUpdatedAtBefore(Timestamp updatedAt);

//    @Query(value = "select * from user.user where updatedAt<:updatedAt", nativeQuery = true)
//    public List<User> findByUpdatedAtBeforeNativeQuery(@Param("updatedAt") Timestamp updatedAt);

    public List<User> findTop1ByUsername(String string);

    public List<User> findTop10000ByUpdatedAtBefore(Timestamp timestamp);
}