package com.user.center.dto.request;

import com.user.center.util.AllowedValues;
import com.user.center.util.DayAfter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@ApiModel(value = "New User request body", description = "Information to create a new user")
@Data
public class AddUserDetailReq {

    @ApiModelProperty(value = "Source of the request", example = "EXT, INT", required = true)
    @AllowedValues(message = "Source value is not in scope", allowedValues = "EXT,ext,INT,int")
    private String source;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @Valid
    private AddProfileReq newProfileReq;

    /**
     * Test
     * Note: when LocalDateTime serialize into JSON, will be something like this:
     * "day":{"dayOfMonth":22,"dayOfWeek":"THURSDAY","dayOfYear":203,"hour":13,"minute":25,"month":"JULY","monthValue":7,"nano":920000000,"second":35,"year":2021,"chronology":{"id":"ISO","calendarType":"iso8601"}}
     */
    @DayAfter(message = "Day is not valid", dayAfter = 4)
    private String day;

}
