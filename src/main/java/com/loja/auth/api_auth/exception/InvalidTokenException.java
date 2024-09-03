package com.loja.auth.api_auth.exception;

public class InvalidTokenException extends RuntimeException {

	private static final long serialVersionUID = -4380668315469534601L;

	public InvalidTokenException(String message) {
        super(message);
    }
}