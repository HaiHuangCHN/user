package com.user.dao.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user", schema = "user")
public class User extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3213557585267891435L;

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "username", length = 255)
    @Size(min = 1, max = 255, message = "username exceed length constraint")
    private String username;

    @Column(name = "password", length = 255)
    @Size(min = 1, max = 255, message = "password exceed length constraint")
    private String password;

    @Column(name = "private_account")
    private Boolean privateAccount = false;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Profile profile;

    public User() {
        super();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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

    public Boolean getPrivateAccount() {
        return privateAccount;
    }

    public void setPrivateAccount(Boolean privateAccount) {
        this.privateAccount = privateAccount;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", privateAccount=" + privateAccount + ", profile=" + profile + "]";
    }

}