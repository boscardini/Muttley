package com.example.Muttley.infra.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final SecurityInterceptor securityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Registra o nosso filtro de segurança customizado no motor de execução do Spring
        registry.addInterceptor(securityInterceptor)
                .addPathPatterns("/**"); // O padrão "/**" diz que o filtro deve olhar todas as pastas e subpastas
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configuração de CORS essencial para permitir que o Next.js (porta 3000) converse com o Java (porta 8083)
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // Permite chamadas vindas do seu servidor de front-end
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}