package com.loja.auth.api_auth.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.loja.auth.api_auth.exception.AuthenticationFailureException;
import com.loja.auth.api_auth.model.entity.Auth;
import com.loja.auth.api_auth.repository.AuthRepository;

@Service
public class TokenService {

    private static final String SECRET = "secreta";
    private static final String ISSUER = "auth";
    
    @Autowired
    private AuthRepository userRepository;

    public String gerarToken(Auth user) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(user.getUsername())
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole().name())
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
    
 // Método para validar o token
    public boolean isTokenValid(String token) {
        try {
            // Verifica se o token é assinado com a chave secreta
            JWT.require(Algorithm.HMAC256(SECRET))
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token);
            return true; // Token válido
        } catch (TokenExpiredException e) {
            return false; // Token expirado
        } catch (JWTVerificationException e) {
            return false; // Token inválido
        } catch (Exception e) {
            return false; // Erro inesperado
        }
    }

    // Método para obter o usuário a partir do token
    public Auth getUserFromToken(String token) {
        String username = getSubject(token); // Obtém o nome de usuário do token
        return userRepository.findByLogin(username); // Busca o usuário no banco de dados
    }
}
