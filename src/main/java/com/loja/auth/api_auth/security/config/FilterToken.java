package com.loja.auth.api_auth.security.config;

import java.io.IOException;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.loja.auth.api_auth.exception.AuthenticationFailureException;
import com.loja.auth.api_auth.model.entity.Auth;
import com.loja.auth.api_auth.repository.AuthRepository;
import com.loja.auth.api_auth.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterToken extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(FilterToken.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.replace("Bearer ", "");
            try {
                String subject = tokenService.getSubject(token);
                if (Objects.nonNull(subject)) {
                    Auth user = userRepository.findByLogin(subject);
                    if (Objects.nonNull(user)) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    } else {
                        logger.warn("Usuário não encontrado: {}", subject);
                        throw new AuthenticationFailureException("Usuário não encontrado.");
                    }
                } else {
                    logger.warn("Token inválido: {}", token);
                    throw new AuthenticationFailureException("Token inválido.");
                }
            } catch (AuthenticationFailureException e) {
                logger.error("Erro de autenticação: ", e);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write(e.getMessage());
                response.getWriter().flush();
                return; // Interromper a cadeia de filtros aqui
            } catch (Exception e) {
                logger.error("Erro inesperado ao autenticar o token", e);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Token inválido ou expirado: " + e.getMessage());
                response.getWriter().flush();
                return; // Interromper a cadeia de filtros aqui
            }
        }

        filterChain.doFilter(request, response);
    }

}
