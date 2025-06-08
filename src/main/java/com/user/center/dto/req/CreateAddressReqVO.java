package com.user.center.dto.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateAddressReqVO {

    @NotNull
    private String country;

    @NotNull
    private String province;

    @NotNull
    private String city;

    @NotNull
    private String district;

    @NotNull
    private String detailAddress;

    @NotNull
    private String postcode;

}