package com.user.center.dto.res;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "User Profile Response Body", description = "Information returned when sucessfully created")
public class ProfileResp {

    @JsonInclude(Include.NON_NULL)
    private String errorMsg;

    @JsonInclude(Include.NON_NULL)
    private String sign;

    @JsonInclude(Include.NON_NULL)
    private ProfileInfo profileInfo;

    private List<AddressResp> addressResp;

}
