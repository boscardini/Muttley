package com.example.Muttley.infra.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    // Chave secreta de 256 bits para assinar digitalmente o Token
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    // O token expira em 2 horas (em milissegundos)
    private final long TEMPO_EXPIRACAO = 7200000; 

    // Gera o passaporte (Token) salvando o ID, o Login (CPF ou Usuário) e o tipo de acesso
    public String gerarToken(Long id, String login, String tipoUsuario) {
        Date agora = new Date();
        Date dataExpiracao = new Date(agora.getTime() + TEMPO_EXPIRACAO);

        return Jwts.builder()
                .setSubject(login)
                .claim("id", id)
                .claim("role", tipoUsuario) // "ALUNO" ou "GESTOR"
                .setIssuedAt(agora)
                .setExpiration(dataExpiracao)
                .signWith(key)
                .compact();
    }

    // Abre o token e lê o que está escrito dentro
    public Claims extrairClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Verifica se o token ainda é válido
    public boolean isTokenValido(String token) {
        try {
            Claims claims = extrairClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}