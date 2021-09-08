package com.user.center.dao.entity;

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
@Table(name = "user_detail", schema = "user_center")
public class UserDetail extends BaseEntity implements Serializable {


    private static final long serialVersionUID = -3213557585267891435L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username", nullable = false)
    @Size(min = 1, max = 255, message = "username exceed length constraint")
    private String username;

    @Column(name = "password", nullable = false)
    @Size(min = 1, max = 255, message = "password exceed length constraint")
    private String password;

    /**
     * TODO Will default value has impact on update action, wrongly update into false?
     */
    @Column(name = "private_account", nullable = false)
    private Boolean privateAccount = false;

    @OneToOne(mappedBy = "userDetail", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Profile profile;

    public UserDetail() {
        super();
    }

    public UserDetail(String username, String password) {
        this.username = username;
        this.password = password;
    }

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


}