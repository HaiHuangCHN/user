package com.user.center.dao.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_detail_arch", schema = "user_center")
public class UserDetailArch extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @NotNull(message = "userId can not be null")
    private Integer id;

    @Column(name = "username")
    @Size(min = 1, max = 255, message = "username exceed length constraint")
    private String username;

    @Column(name = "password")
    @Size(min = 1, max = 255, message = "password exceed length constraint")
    private String password;

    @OneToOne(mappedBy = "userDetail", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ProfileArch profile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ProfileArch getProfile() {
        return profile;
    }

    public void setProfile(ProfileArch profile) {
        this.profile = profile;
    }
}