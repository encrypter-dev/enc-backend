package com.encrypter.enc.exceptions;

import com.encrypter.enc.constants.GlobalConstants;
import com.encrypter.enc.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<ResponseDTO<IllegalArgumentException>> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException e) {
		log.error(e.getMessage(), e);
		ResponseDTO<IllegalArgumentException> responseDto = new ResponseDTO<>(GlobalConstants.RESPONSE_FAILED, e);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
	}

	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public ResponseEntity<ResponseDTO<HttpMessageNotReadableException>> handleHttpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException e) {
		log.error(e.getMessage(), e);
		ResponseDTO<HttpMessageNotReadableException> responseDto = new ResponseDTO<>(GlobalConstants.RESPONSE_FAILED, e);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
	}

	@ExceptionHandler(value = MissingServletRequestParameterException.class)
	public ResponseEntity<ResponseDTO<MissingServletRequestParameterException>> handleMissingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException e) {
		log.error(e.getMessage(), e);
		ResponseDTO<MissingServletRequestParameterException> responseDto = new ResponseDTO<>(GlobalConstants.RESPONSE_FAILED, e);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
	}

	/**
	 * Handles all remaining exceptions from the rest controller.
	 * <p>
	 * This acts as a catch-all for any exceptions not handled by previous exception handlers.
	 *
	 * @param e Exception
	 * @return error response POJO
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ResponseDTO<Exception>> handleException(HttpServletRequest request, Exception e) {
		log.error(e.getMessage(), e);
		ResponseDTO<Exception> responseDto = new ResponseDTO<>(GlobalConstants.RESPONSE_FAILED, e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
	}

}
