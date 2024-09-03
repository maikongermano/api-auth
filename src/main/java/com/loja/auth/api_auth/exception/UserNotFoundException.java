package com.loja.auth.api_auth.exception;

public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 8438175179617973667L;

	public UserNotFoundException(String message) {
        super(message);
    }
}
