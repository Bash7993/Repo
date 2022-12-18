package com.wipro.demo.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	ExceptionResponse handleResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest req) {
		ExceptionResponse myResponse = new ExceptionResponse();
		myResponse.setErrorMessage(exception.getMessage());
		myResponse.setRequestURI(req.getRequestURI());
		return myResponse;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	ExceptionResponse handleException(Exception exception, HttpServletRequest req) {
		ExceptionResponse myResponse = new ExceptionResponse();
		myResponse.setErrorMessage(exception.getMessage());
		myResponse.setRequestURI(req.getRequestURI());
		return myResponse;
	}
}
