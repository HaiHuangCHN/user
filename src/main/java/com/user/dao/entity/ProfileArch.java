package com.user.dao.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "profile_arch", schema = "user")
public class ProfileArch extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "profile_arch_id", nullable = false)
    @NotNull(message = "profile_arch_id can not be null")
    private Integer profileId;

    @Column(name = "email")
    @Size(min = 1, max = 50, message = "email exceed length constraint")
    private String email;

    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "sex cannot be null")
    private SexEnum sex;

    @Column(name = "phone_num", length = 11)
    @Size(max = 11, message = "phoneNum exceed length constraint")
    private String phoneNum;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_arch_id", referencedColumnName = "user_arch_id", nullable = false)
    private UserArch user;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AddressArch> addressList;

    public ProfileArch() {
        super();
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public UserArch getUser() {
        return user;
    }

    public void setUser(UserArch user) {
        this.user = user;
    }

    public List<AddressArch> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<AddressArch> addressList) {
        this.addressList = addressList;
    }

    @Override
    public String toString() {
        return "ProfileArch [profileId=" + profileId + ", email=" + email + ", sex=" + sex + ", phoneNum=" + phoneNum + ", user=" + user + ", addressList=" + addressList + "]";
    }

}