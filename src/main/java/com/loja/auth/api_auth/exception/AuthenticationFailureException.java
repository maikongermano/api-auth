package com.loja.auth.api_auth.exception;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class AuthenticationFailureException extends RuntimeException {

	private static final long serialVersionUID = -5987152406807428726L;

    public AuthenticationFailureException(String message) {
        super(message);
    }

    public AuthenticationFailureException(String message, ZonedDateTime expirationDate) {
        super(formatMessageWithDate(message, expirationDate));
    }

    private static String formatMessageWithDate(String message, ZonedDateTime expirationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
        String formattedDate = expirationDate.format(formatter);
        return String.format("%s: O token expirou em %s", message, formattedDate);
    }
}
