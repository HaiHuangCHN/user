package com.user.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "User Profile Response Body", description = "Information returned when sucessfully created")
public class ProfileResp {
    @JsonInclude(Include.NON_NULL)
    private ProfileInfo profileInfo;
    @JsonInclude(Include.NON_NULL)
    private String sign;
    private List<AddressResp> addressResp;
    @JsonInclude(Include.NON_NULL)
    private String errorMsg;

    public ProfileInfo getProfileInfo() {
        return profileInfo;
    }

    public void setProfileInfo(ProfileInfo profileInfo) {
        this.profileInfo = profileInfo;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public List<AddressResp> getAddressResp() {
        return addressResp;
    }

    public void setAddressResp(List<AddressResp> addressResp) {
        this.addressResp = addressResp;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "ProfileResp [profileInfo=" + profileInfo + ", sign=" + sign + ", errorMsg=" + errorMsg + "]";
    }

}
