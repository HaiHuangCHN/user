package com.user.center.dto.res;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResBody {

    private String code;

    private String message;

    private String detail;

}
