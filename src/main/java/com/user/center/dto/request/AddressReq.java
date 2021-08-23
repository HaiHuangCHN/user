package com.user.center.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddressReq {

    @NotNull
    private String country;

    private String province;

    private String city;

    private String district;

    private String detailAddress;

    private String postcode;

}