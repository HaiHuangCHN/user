package com.user.center.exception;

import com.user.center.costant.Constants;
import com.user.center.dto.res.ErrorResBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
// @Slf4j is with the same log output compared to the traditional style
@Slf4j
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Object> errorHandler(Exception e) throws Exception {
        log.error(e.getMessage(), e);
        ErrorResBody errorResBody = new ErrorResBody();
        HttpStatus httpStatus = null;
        if (e instanceof BusinessException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            errorResBody.setSource(Constants.SOURCE);
            errorResBody.setCode(((BusinessException) e).getCode());
            errorResBody.setMessage(e.getMessage());
            errorResBody.setDetail(((BusinessException) e).getDetail());
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
            errorResBody.setSource(Constants.SOURCE);
            errorResBody.setMessage(Constants.UNEXPECTED_ERROR + ": " + e.getMessage());
        }

        return ResponseEntity.status(httpStatus).body(errorResBody);
    }

}
