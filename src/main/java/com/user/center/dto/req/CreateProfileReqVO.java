package com.user.center.dto.req;

import com.user.center.util.validation.AgeAnno;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class CreateProfileReqVO {


    private Boolean isVip;

    @Size(max = 50, message = "email exceed length constraint")
    @Email(message = "email is not valid")
    private String email;

    @NotNull(message = "Sex cannot be null")
    private String sex;

    private String phoneNum;

    @AgeAnno(message = "Age is not valid")
    private Integer age;

    @Valid
    @NotEmpty
    private List<CreateAddressReqVO> createAddressReq;


}
