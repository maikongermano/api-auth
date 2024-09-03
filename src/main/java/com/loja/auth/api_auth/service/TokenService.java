package com.loja.auth.api_auth.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.loja.auth.api_auth.exception.AuthenticationFailureException;
import com.loja.auth.api_auth.model.entity.Auth;

@Service
public class TokenService {

    private static final String SECRET = "secreta";
    private static final String ISSUER = "auth";

    public String gerarToken(Auth user) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(user.getUsername())
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole())
                .withClaim("empresa", user.getCompany().getId())
                .withExpiresAt(LocalDateTime.now()
                        .plusMinutes(10)
                        .toInstant(ZoneOffset.of("-03:00"))
                ).sign(Algorithm.HMAC256(SECRET));
    }

    public String getSubject(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(SECRET))
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (TokenExpiredException e) {
            // Captura a data de expiração do token
            Instant expiredAt = e.getExpiredOn();
            String formattedDate = expiredAt.atZone(ZoneId.systemDefault())
                                             .format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm"));
            
            // Lança a exceção com a mensagem formatada
            throw new AuthenticationFailureException("Token inválido ou expirado: O token expirou em " + formattedDate);
        } catch (JWTVerificationException e) {
            // Captura e trata qualquer outra exceção específica relacionada à verificação do JWT
            throw new AuthenticationFailureException("Token inválido ou expirado: " + e.getMessage());
        } catch (Exception e) {
            // Captura outras exceções inesperadas
            throw new AuthenticationFailureException("Erro inesperado ao autenticar o token: " + e.getMessage());
        }
    }
}
