package com.sena.BusinessAssistantSpring.exception;

import org.springframework.validation.ObjectError;

import java.util.List;

public class BusinessValidationException extends RuntimeException {

    /**
	 * excepcion creada para la validacion de reglas personalizadas para jpa
	 */
	private static final long serialVersionUID = 1L;
	private final List<ObjectError> errors;

    public BusinessValidationException(List<ObjectError> errors) {
        this.errors = errors;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }
}
