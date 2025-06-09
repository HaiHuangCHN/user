package com.user.center.dao.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.user.center.dao.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {


    @Query(value = "select * from user_center.user_detail where username=:username and password=:password", nativeQuery = true)
    UserDetail findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    UserDetail findByUsername(String username);

    List<UserDetail> findByUpdatedAtBefore(LocalDateTime updatedAt);

//    @Query(value = "select * from user_center.user_detail where updatedAt<:updatedAt", nativeQuery = true)
//    public List<User> findByUpdatedAtBeforeNativeQuery(@Param("updatedAt") Timestamp updatedAt);

    List<UserDetail> deleteByUpdatedAtBefore(LocalDateTime updatedAt);

    List<UserDetail> findTop1ByUsername(String string);

    List<UserDetail> findTop10000ByUpdatedAtBefore(Timestamp timestamp);


}