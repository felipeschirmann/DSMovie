package com.devsuperior.dsmovie.controllers.handlers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.devsuperior.dsmovie.dto.CustomError;
import com.devsuperior.dsmovie.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

public class ControllerExceptionHandlerTest {

	private final ControllerExceptionHandler handler = new ControllerExceptionHandler();

	@Test
	public void resourceNotFoundShouldReturnNotFoundResponse() {
		ResourceNotFoundException exception = new ResourceNotFoundException("Test message");
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		Mockito.when(request.getRequestURI()).thenReturn("/movies/999");

		ResponseEntity<CustomError> response = handler.resourceNotFound(exception, request);

		Assertions.assertNotNull(response);
		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		Assertions.assertEquals("Resource not found", response.getBody().getError());
		Assertions.assertEquals("Test message", response.getBody().getMessage());
		Assertions.assertEquals("/movies/999", response.getBody().getPath());
	}
}
