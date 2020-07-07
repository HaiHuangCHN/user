package com.user.dto.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddProfileReq {
    private String phoneNum;

    private String email;

    @NotNull
    @Size(message = "Sex should be valid")
    private String sex;

    private Integer age;

    private Boolean isMarried;

    @Valid
    private List<AddressReq> addressReqList;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<AddressReq> getAddressReqList() {
        return addressReqList;
    }

    public void setAddressReqList(List<AddressReq> addressReqList) {
        this.addressReqList = addressReqList;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getIsMarried() {
        return isMarried;
    }

    public void setIsMarried(Boolean isMarried) {
        this.isMarried = isMarried;
    }

}
