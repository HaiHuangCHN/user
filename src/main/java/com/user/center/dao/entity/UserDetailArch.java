package com.user.center.dao.entity;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Entity
@Table(name = "user_detail_arch", schema = "user_center")
public class UserDetailArch extends BaseEntity implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(mappedBy = "userDetail", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ProfileArch profile;

}