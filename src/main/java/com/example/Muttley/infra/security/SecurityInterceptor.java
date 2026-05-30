package com.example.Muttley.infra.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class SecurityInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        String uri = request.getRequestURI();
        if (uri.startsWith("/auth/") || uri.contains("swagger") || uri.contains("api-docs")) {
            return true;
        }

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // HTTP 401
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\": \"Acesso negado: Token de autenticação ausente.\"}");
            return false; // Retornar false bloqueia a requisição imediatamente
        }

        String token = authHeader.substring(7);

        if (!tokenService.isTokenValido(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // HTTP 401
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\": \"Acesso negado: Token inválido ou expirado.\"}");
            return false;
        }

        Claims claims = tokenService.extrairClaims(token);
        String role = claims.get("role", String.class);

        request.setAttribute("usuarioId", claims.get("id", Long.class));
        request.setAttribute("usuarioRole", role);
        request.setAttribute("usuarioLogin", claims.getSubject());


        if (uri.startsWith("/admin/") && !"ADMIN".equals(role)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // HTTP 403 Forbidden
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"error\": \"Acesso proibido: Esta área requer privilégios de Administrador.\"}");
            return false;
        }

        return true; // Autorizado com sucesso!
    }
}