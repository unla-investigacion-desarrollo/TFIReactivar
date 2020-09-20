package com.unla.reactivar.exceptions.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.unla.reactivar.exceptions.IncorrectTokenOrTokenExpiredPwd;
import com.unla.reactivar.exceptions.IncorrectUserOrPassword;
import com.unla.reactivar.exceptions.InvalidCuilCuit;
import com.unla.reactivar.exceptions.ObjectAlreadyExists;
import com.unla.reactivar.exceptions.ObjectNotFound;
import com.unla.reactivar.exceptions.QrExporterException;
import com.unla.reactivar.exceptions.UserIsAlreadyActive;
import com.unla.reactivar.exceptions.UserIsAlreadyInactive;
import com.unla.reactivar.exceptions.models.GenericError;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class ReactivarExceptionHandler {

	@ExceptionHandler(ObjectNotFound.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	protected GenericError ObjectNotFoundExceptionHandler(ObjectNotFound ex) {

		String message = "El objeto seleccionado no fue encontrado.";

		if (!StringUtils.isBlank(ex.getErrorMessage())) {
			message = new StringBuilder(message).append(" (").append(ex.getErrorMessage()).append(")").toString();
		}

		GenericError error = new GenericError("error.reactivar.object.not_found", message);

		return error;
	}

	@ExceptionHandler(IncorrectUserOrPassword.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	protected GenericError incorrectUserOrPasswordExceptionHandler(IncorrectUserOrPassword ex) {

		String message = "Usuario y/o Contraseña incorrecto";

		GenericError error = new GenericError("error.reactivar.incorrect.user_password", message);

		return error;
	}

	@ExceptionHandler(QrExporterException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	protected GenericError qrExporterExceptionHandler(QrExporterException ex) {

		String message = "Ha ocurrido un error al generar PDF.";

		GenericError error = new GenericError("error.reactivar.exporter.pdf", message);

		return error;
	}

	@ExceptionHandler(ObjectAlreadyExists.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	protected GenericError ObjectAlreadyExistsExceptionHandler(ObjectAlreadyExists ex) {

		String message = "El objeto ya exite dentro de la BD.";

		GenericError error = new GenericError("error.reactivar.db.registro_ya_existente", message);

		return error;
	}
	
	@ExceptionHandler(IncorrectTokenOrTokenExpiredPwd.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	protected GenericError IncorrectTokenOrTokenExpiredPwdExceptionHandler(IncorrectTokenOrTokenExpiredPwd ex){
		
		String message = "El token para la recuperacion de pwd/validacion de email es invalido/vencido.";
		
		GenericError error = new GenericError("error.reactivar.invalid.token.pwd.mail", message);
		
		return error;
	}
	
	@ExceptionHandler(InvalidCuilCuit.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	protected GenericError InvalidCuilCuitExceptionHandler(InvalidCuilCuit ex){
		
		String message = "El cuil / cuit es invalido";
		
		GenericError error = new GenericError("error.reactivar.invalid.cuil", message);
		
		return error;
	}
	
	@ExceptionHandler(UserIsAlreadyActive.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	protected GenericError UserIsAlreadyActiveExceptionHandler(UserIsAlreadyActive ex){
		
		String message = "Usuario ya se encuentra verificado";
		
		GenericError error = new GenericError("error.reactivar.user.already_active", message);
		
		return error;
	}
	
	@ExceptionHandler(UserIsAlreadyInactive.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	protected GenericError UserIsAlreadyInactiveExceptionHandler(UserIsAlreadyInactive ex){
		
		String message = "Usuario no se encuentra verificado";
		
		GenericError error = new GenericError("error.reactivar.user.already_inactive", message);
		
		return error;
	}

}
