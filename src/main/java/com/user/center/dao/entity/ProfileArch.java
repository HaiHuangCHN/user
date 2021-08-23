package com.user.center.dao.entity;

import lombok.Getter;
import lombok.Setter;

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

@Getter
@Setter
@Entity
@Table(name = "profile_arch", schema = "user_center")
public class ProfileArch extends BaseEntity implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @NotNull(message = "profile_arch_id can not be null")
    private Integer id;

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
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserDetailArch userDetail;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AddressArch> addressList;

    public ProfileArch() {
        super();
    }


}