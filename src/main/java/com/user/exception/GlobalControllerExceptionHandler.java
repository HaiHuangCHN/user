package com.user.exception;

import com.user.costant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<Object> errorHandler(Exception e) throws Exception {
        logger.error(e.getMessage(), e);
        ErrorBody errorBody = new ErrorBody();
        HttpStatus httpStatus = null;
        if (e instanceof ErrorResponseException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            errorBody.setSource(Constants.SOURCE);
            errorBody.setCode(((ErrorResponseException) e).getCode());
            errorBody.setMessage(((ErrorResponseException) e).getMessage());
            errorBody.setDetail(((ErrorResponseException) e).getDetail());
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
            errorBody.setMessage(Constants.UNEXPECTED_ERROR + " : " + e.getMessage());
        }
        return ResponseEntity.status(httpStatus).body(errorBody);
    }

}
