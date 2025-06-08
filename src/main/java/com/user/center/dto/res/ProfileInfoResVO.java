package com.user.center.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileInfoResVO {

    @ApiModelProperty(value = "Email of the user", example = "your.emial.address@mail.com", required = true)
    @JsonInclude(Include.NON_NULL)
    private String email;

    @JsonInclude(Include.NON_NULL)
    private String sex;

    @JsonInclude(Include.NON_NULL)
    private String phoneNum;

    @Override
    public String toString() {
        return "ProfileInfoResVO{" +
                "email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }

}
