package com.unla.alimentar.exceptions.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.unla.alimentar.exceptions.ObjectNotFound;
import com.unla.alimentar.exceptions.models.GenericError;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class AlimentarExceptionHandler {

	
	@ExceptionHandler(ObjectNotFound.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	protected GenericError servletRequestBindingExceptionHandler(ObjectNotFound ex){
		
		String message = "El objeto seleccionado no fue encontrado.";
		
		if(!StringUtils.isBlank(ex.getErrorMessage())) {
			message = new StringBuilder(message).append(" (").append(ex.getErrorMessage()).append(")").toString();
		}
		
		GenericError error = new GenericError("error.alimentar.object.not_found", message);
		
		return error;
	}
}
