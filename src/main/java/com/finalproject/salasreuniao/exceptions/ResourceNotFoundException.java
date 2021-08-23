package com.finalproject.salasreuniao.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception{

	private static final long serialVersionUID = -8990138096174027941L;
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
	
}
