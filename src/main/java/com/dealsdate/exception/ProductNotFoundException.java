package com.dealsdate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductNotFoundException extends Exception{
	
	private static final long serialVersionUID = 1L;
	private String message;

}
