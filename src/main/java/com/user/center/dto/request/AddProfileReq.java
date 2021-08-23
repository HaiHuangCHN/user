package com.user.center.dto.request;

import com.user.center.util.AgeAnno;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AddProfileReq {


    private String phoneNum;

    private String email;

    @NotNull(message = "Sex cannot be null")
    private String sex;

    @AgeAnno(message = "Age is not valid")
    private Integer age;

    private Boolean isMarried;

    @Valid
    private List<AddressReq> addressReqList;


}
