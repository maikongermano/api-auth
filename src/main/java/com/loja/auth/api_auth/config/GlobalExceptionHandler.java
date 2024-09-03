package com.loja.auth.api_auth.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.loja.auth.api_auth.exception.AuthenticationFailureException;
import com.loja.auth.api_auth.exception.CustomAuthenticationException;
import com.loja.auth.api_auth.exception.InvalidTokenException;
import com.loja.auth.api_auth.exception.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> handleInvalidTokenException(InvalidTokenException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationFailureException.class)
    public ResponseEntity<String> handleAuthenticationFailureException(AuthenticationFailureException ex) {
        System.out.println("AuthenticationFailureException capturada");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        System.out.println("Exceção geral capturada: " + ex.getMessage());
        return new ResponseEntity<>("Erro interno do servidor: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(CustomAuthenticationException.class)
    public String handleCustomAuthenticationException(CustomAuthenticationException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "redirect:/login";
    }
}

