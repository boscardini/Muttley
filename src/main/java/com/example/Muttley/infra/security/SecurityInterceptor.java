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
        
        // 1. Liberação do preflight request do CORS (OPTIONS)
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        // Utilizar getServletPath() evita problemas com barras extras ou caminhos de contexto
        String path = request.getServletPath();
        
        // 2. Liberação do Cadastro de Alunos/Participantes (Sem exigência de Token)
        if (request.getMethod().equals("POST") && path.startsWith("/participantes")) {
            return true; 
        }

        // 3. Liberação da Vitrine Pública de Eventos (Apenas método GET)
        if (request.getMethod().equals("GET") && path.startsWith("/eventos")) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                if (tokenService.isTokenValido(token)) {
                    Claims claims = tokenService.extrairClaims(token);
                    request.setAttribute("usuarioId", claims.get("id", Long.class));
                    request.setAttribute("usuarioRole", claims.get("role", String.class));
                }
            }
            return true; 
        }

        // 4. Liberação das rotas de autenticação e documentação
        if (path.startsWith("/auth") || path.contains("swagger") || path.contains("api-docs")) {
            return true; 
        }

        // 5. Validação do cabeçalho Authorization para rotas protegidas
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"mensagem\": \"Acesso negado: Token de autenticação ausente.\"}");
            return false; 
        }

        String token = authHeader.substring(7); 
        if (!tokenService.isTokenValido(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"mensagem\": \"Acesso negado: Token inválido ou expirado.\"}");
            return false;
        }

        // 6. Injeção do contexto do usuário logado
        Claims claims = tokenService.extrairClaims(token);
        String role = claims.get("role", String.class);

        request.setAttribute("usuarioId", claims.get("id", Long.class));
        request.setAttribute("usuarioRole", role);
        request.setAttribute("usuarioLogin", claims.getSubject());

        // Trava para gerenciamento de apresentadores (Apenas ADMIN)
        if (path.startsWith("/apresentadores")) {
            boolean isGet = request.getMethod().equals("GET");
            // Se NÃO for ADMIN, e também (NÃO for um GET feito por um GESTOR), barra!
            if (!"ADMIN".equals(role) && !(isGet && "GESTOR".equals(role))) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); 
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"mensagem\": \"Acesso proibido: Apenas o Administrador pode cadastrar ou modificar apresentadores.\"}");
                return false;
            }
        }

        // Trava para rotas administrativas gerais
        if (path.startsWith("/admin") && !"ADMIN".equals(role)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); 
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"mensagem\": \"Acesso proibido: Esta área requer privilégios de Administrador.\"}");
            return false;
        }

        return true; 
    }
}