package com.user.center.exception;

import com.user.center.costant.Constants;
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
        ErrorBody errorBody = new ErrorBody();
        HttpStatus httpStatus = null;
        if (e instanceof BusinessException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            errorBody.setSource(Constants.SOURCE);
            errorBody.setCode(((BusinessException) e).getCode());
            errorBody.setMessage(((BusinessException) e).getMessage());
            errorBody.setDetail(((BusinessException) e).getDetail());
        } else if (e instanceof InputParameterException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            errorBody.setSource(Constants.SOURCE);
            errorBody.setCode(((InputParameterException) e).getCode());
            errorBody.setMessage(((InputParameterException) e).getMessage());
            errorBody.setDetail(((InputParameterException) e).getDetail());
        } else if (e instanceof TokenException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            errorBody.setSource(Constants.SOURCE);
            errorBody.setCode(((TokenException) e).getCode());
            errorBody.setMessage(((TokenException) e).getMessage());
            errorBody.setDetail(((TokenException) e).getDetail());
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
            errorBody.setSource(Constants.SOURCE);
            errorBody.setMessage(Constants.UNEXPECTED_ERROR + ": " + e.getMessage());
        }
        return ResponseEntity.status(httpStatus).body(errorBody);
    }

}
