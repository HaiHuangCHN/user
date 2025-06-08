package com.user.center.dto.res;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "AddressResp object model", description = "Address Information")
public class AddressResp {


    private String country;

    private String province;

    private String city;

    private String district;

    private String detailAddress;

    private String postcode;


}
