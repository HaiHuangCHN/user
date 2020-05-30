package com.user.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "address_arch", schema = "shopping")
public class AddressArch extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "address_arch_id", nullable = false)
    @NotNull(message = "address_id can not be null")
    private Integer addressId;

    @Column(name = "country")
    private String country;

    @Column(name = "provience")
    private String provience;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "detail_address")
    private String detailAddress;

    @Column(name = "postcode")
    private String postcode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_arch_id", referencedColumnName = "profile_arch_id", nullable = false)
    private ProfileArch profile;

    public AddressArch() {
        super();
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvience() {
        return provience;
    }

    public void setProvience(String provience) {
        this.provience = provience;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public ProfileArch getProfile() {
        return profile;
    }

    public void setProfile(ProfileArch profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "AddressArch [addressId=" + addressId + ", country=" + country + ", provience=" + provience + ", city=" + city + ", district=" + district + ", detailAddress="
                + detailAddress + ", postcode=" + postcode + ", profile=" + profile + "]";
    }

}