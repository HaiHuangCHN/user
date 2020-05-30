package com.user.entity;

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
@Table(name = "user_arch", schema = "user")
public class UserArch extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_arch_id", nullable = false)
    @NotNull(message = "userId can not be null")
    private Integer userId;

    @Column(name = "username", length = 255)
    @Size(min = 1, max = 255, message = "username exceed length constraint")
    private String username;

    @Column(name = "password", length = 255)
    @Size(min = 1, max = 255, message = "password exceed length constraint")
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ProfileArch profile;

    public UserArch() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "UserArch [userId=" + userId + ", username=" + username + ", password=" + password + ", profile=" + profile + "]";
    }

}