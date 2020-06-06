package com.user.dto.request;

import javax.validation.Valid;

import com.user.util.PossibleValuesCheckAnno;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "New User request body", description = "Information to create a new user")
public class AddUserReq {

    @ApiModelProperty(value = "source of the request", example = "EXT, INT", allowEmptyValue = false, required = true)
    @PossibleValuesCheckAnno(message = "source value is not in scope", possibleValues = "EXT,ext,INT,int")
    private String source;

    private String username;

    private String password;

    @Valid
    private AddProfileReq newProfileReq;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AddProfileReq getNewProfileReq() {
        return newProfileReq;
    }

    public void setNewProfileReq(AddProfileReq newProfileReq) {
        this.newProfileReq = newProfileReq;
    }

}
