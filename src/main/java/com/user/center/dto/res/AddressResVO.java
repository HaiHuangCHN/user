package com.user.center.dto.res;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "AddressResVO object model", description = "Address Information")
public class AddressResVO {


    private String country;

    private String province;

    private String city;

    private String district;

    private String detailAddress;

    private String postcode;


}
