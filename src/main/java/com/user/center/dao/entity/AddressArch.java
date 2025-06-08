package com.user.center.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "address_arch", schema = "shopping")
public class AddressArch extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "provience", nullable = false)
    private String provience;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "detail_address", nullable = false)
//    @Column(name = "detail_Address", nullable = false), it works
//    @Column(name = "detailAddress", nullable = false), it works
//    @Column(name = "DETAIL_ADDRESS", nullable = false), it works (it is no matter whether it is written in upper case or lower case)
    private String detailAddress;

    @Column(name = "postcode", nullable = false)
    private String postcode;

    //    @Column(s) not allowed on a @ManyToOne property
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
    private ProfileArch profile;

    public AddressArch() {
        super();
    }


}