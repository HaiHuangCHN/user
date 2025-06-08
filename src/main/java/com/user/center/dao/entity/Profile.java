package com.user.center.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "profile", schema = "user_center")
public class Profile extends BaseEntity implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private UserDetail userDetail;

    // @OneToMany must be used on collection
    // CascadeType.REMOVE for archive
    @OneToMany(mappedBy = "profile", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Address> addressList;

    public Profile() {
        super();
    }


}