package com.loja.auth.api_auth.exception;

public class CustomAuthenticationException extends RuntimeException {

	private static final long serialVersionUID = -3266655639395228598L;

	public CustomAuthenticationException(String message) {
        super(message);
    }
}

