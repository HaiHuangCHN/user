package com.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.user.costant.Constants;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResponseEntity<Object> errorHandler(Exception e) throws Exception {
		ErrorBody errorBody = new ErrorBody();
		HttpStatus httpStatus = null;
		e.printStackTrace();
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
