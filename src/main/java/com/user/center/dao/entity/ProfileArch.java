package com.user.center.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "profile_arch", schema = "user_center")
public class ProfileArch extends BaseEntity implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * TODO hai Will default value has impact on update action, wrongly update into false?
     */
    @Column(name = "is_vip", nullable = false)
    private Boolean isVip = false;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "sex", nullable = false)
    @Enumerated(EnumType.STRING)
    private SexEnum sex;

    @Column(name = "phone_num", length = 11)
    private String phoneNum;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserDetailArch userDetail;

    // When you setup in this way, only to save the 'one' is enough
    // @OneToMany must be used on collection
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AddressArch> addressList;

    public ProfileArch() {
        super();
    }


}