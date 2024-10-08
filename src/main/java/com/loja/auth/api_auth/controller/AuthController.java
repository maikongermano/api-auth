package com.loja.auth.api_auth.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loja.auth.api_auth.exception.CustomAuthenticationException;
import com.loja.auth.api_auth.mappers.AuthCompanyMapper;
import com.loja.auth.api_auth.model.dto.AuthCompanyDTO;
import com.loja.auth.api_auth.model.dto.AuthDTO;
import com.loja.auth.api_auth.model.dto.TokenDTO;
import com.loja.auth.api_auth.model.dto.UserDTO;
import com.loja.auth.api_auth.model.entity.Auth;
import com.loja.auth.api_auth.service.TokenService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private TokenService tokenService;
    
    @Autowired
	private HttpServletRequest request;
    
    @Autowired
    private AuthCompanyMapper authCompanyMapper;

    // Endpoint para capturar as informações do usuário logado após o login bem-sucedido via OAuth2
    @GetMapping("/loginSuccess")
    public String getLoginInfo(Model model, @AuthenticationPrincipal OAuth2User principal, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        logger.info("Token: {}", token);

        if (principal == null) {
            logger.warn("Principal is null. Throwing CustomAuthenticationException.");
            throw new CustomAuthenticationException("Usuário não autenticado. Redirecionando para login.");
        }

        try {
            String userName = principal.getAttribute("name");
            if (userName == null) {
                logger.warn("UserName attribute is null. Throwing CustomAuthenticationException.");
                throw new CustomAuthenticationException("Nome do usuário não encontrado. Redirecionando para login.");
            }

            logger.info("User logged in: {}", userName);
            model.addAttribute("name", userName);

            return "loginSuccess"; // Retorna o nome da página de sucesso (Thymeleaf ou outra tecnologia de template)
        } catch (CustomAuthenticationException e) {
            logger.error("Erro de autenticação: ", e);
            throw e; // Relançar a exceção para ser tratada pelo GlobalExceptionHandler
        } catch (Exception e) {
            logger.error("Erro inesperado: ", e);
            model.addAttribute("errorMessage", "Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.");
            return "errorPage";  // Redireciona para uma página de erro apropriada
        }
    }

    // Endpoint para autenticação usando login e senha
    @PostMapping("/auth")
	public String auth(@RequestBody AuthDTO auth) throws JsonProcessingException {
    	logger.info(String.format("User logged in: %s", auth.toString()));
		
        // Cria um token de autenticação com base no login e senha fornecidos
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				auth.getLogin(), auth.getPassword());
		
        // Autentica o usuário
		Authentication authenticate = authenticationManager.authenticate(authenticationToken);

		// Pega o usuário autenticado
		Auth user = (Auth) authenticate.getPrincipal();

		// Gera o token JWT
		TokenDTO token = new TokenDTO(tokenService.gerarToken(user));
		logger.info(String.format("Token logged in: %s", token.toString()));

		// Retorna o token como JSON
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(token);
	}
    
    @GetMapping("/validar-token")
    public ResponseEntity<?> validateToken() {
        // Verifica se o token é válido
    	String token = request.getHeader("Authorization");
    	if (token == null || !token.startsWith("Bearer ")) {
            throw new CustomAuthenticationException("Token inválido: Nenhum token fornecido.");
        }
    	token = token.substring(7); // Remove o prefixo "Bearer " para obter o token real
        if (tokenService.isTokenValid(token)) {
            // Obtém o usuário associado ao token
            Auth user = tokenService.getUserFromToken(token);
            if (user != null) {
                // Retorna as informações do usuário
            	UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getId());
                userDTO.setLogin(user.getUsername());
                userDTO.setRole(user.getRole());
                
                List<AuthCompanyDTO> authCompanyDTOs = user.getAuthCompanies().stream()
                        .map(authCompanyMapper::toDTO)
                        .collect(Collectors.toList());
                userDTO.setAuthCompany(authCompanyDTOs);
                
                return ResponseEntity.ok(userDTO);
            } else {
                return ResponseEntity.status(HttpStatusCode.valueOf(401))
                        .body("Usuário não encontrado para o token fornecido.");
            }
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(403))
                    .body("Token inválido.");
        }
    }
}
