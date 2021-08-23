package com.user.center.dao.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "profile", schema = "user_center")
public class Profile extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 503827839614952904L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", length = 50)
    @Size(max = 50, message = "email exceed length constraint")
    @Email(message = "Email is not valid")
    private String email = "test@mail.com";

    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "sex cannot be null")
    private SexEnum sex;

    @Column(name = "phone_num", length = 11)
    @Size(max = 11, message = "phoneNum exceed length constraint")
    @NotNull(message = "phoneNum cannot be null")
    private String phoneNum;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserDetail userDetail;

    // When you setup in this way, only to save the 'one' is enough
    // @OneToMany must be used on collection
    @OneToMany(mappedBy = "profile", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @Size(min = 1, max = 10, message = "address size shouldn't larger than 10")
    private List<Address> addressList;

    public Profile() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public UserDetail getUser() {
        return userDetail;
    }

    public void setUser(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }


}