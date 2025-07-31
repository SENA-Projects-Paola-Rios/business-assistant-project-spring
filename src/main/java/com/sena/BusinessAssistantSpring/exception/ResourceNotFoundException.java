package com.sena.BusinessAssistantSpring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Indica que cuando se lanza esta excepción, la respuesta será 404 Not Found
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Constructor que acepta un mensaje personalizado
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
