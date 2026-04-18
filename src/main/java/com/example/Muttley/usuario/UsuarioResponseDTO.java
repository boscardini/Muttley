package com.example.Muttley.usuario;

public record UsuarioResponseDTO(
    Long id,
    String nome,
    String email,
    Perfil perfil,
    boolean aprovado
) {}