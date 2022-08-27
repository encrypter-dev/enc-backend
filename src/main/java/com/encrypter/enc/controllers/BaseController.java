package com.encrypter.enc.controllers;

import com.encrypter.enc.dto.ResponseDTO;
import com.encrypter.enc.exceptions.RestExceptionHandler;
import com.encrypter.enc.pojo.ErrorObject;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

public class BaseController extends RestExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseDTO<Object> handleBindingResultErrors(MethodArgumentNotValidException ex, WebRequest request, HttpServletResponse response) {
		List<ErrorObject> errorList = new LinkedList<>();
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			String errorMessage = String.format("%s %s", fieldError.getField(), fieldError.getDefaultMessage());
			errorList.add(new ErrorObject(fieldError.getClass().getSimpleName(), errorMessage));
		}
		for (ObjectError globalError : ex.getBindingResult().getGlobalErrors()) {
			errorList.add(new ErrorObject(globalError.getClass().getSimpleName(), globalError.getDefaultMessage()));
		}
		return new ResponseDTO<>("Errors present!", errorList);
	}
}
