package com.wipro.demo.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExceptionResponse {
	String errorMessage;
	String requestURI;

}
