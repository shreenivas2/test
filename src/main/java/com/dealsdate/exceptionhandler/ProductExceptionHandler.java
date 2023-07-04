package com.dealsdate.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dealsdate.exception.ProductAlreadyExistsException;
import com.dealsdate.exception.ProductNotFoundException;

@RestControllerAdvice
public class ProductExceptionHandler {
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<String> handleProductNotFoundException() {
		return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ProductAlreadyExistsException.class)
	public ResponseEntity<String> handleProductAlreadyExistException() {
		return new ResponseEntity<>("Product Already Exists", HttpStatus.CONFLICT);
	}

}
