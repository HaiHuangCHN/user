package com.user.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

public class ProfileInfo {

    @ApiModelProperty(value = "Email of the user", example = "your.emial.address@mail.com", required = true)
    @JsonInclude(Include.NON_NULL)
    private String email;

    @JsonInclude(Include.NON_NULL)
    private String address;

    @JsonInclude(Include.NON_NULL)
    private String sex;

    @JsonInclude(Include.NON_NULL)
    private String phoneNum;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "ProfileInfo [email=" + email + ", address=" + address + ", sex=" + sex + ", phoneNum=" + phoneNum + "]";
    }

}
