package com.example.Muttley.usuario;

public record UsuarioResponseDTO(
    Long id,
    String nome,
    String email,
    TipoUsuario tipo,
    boolean aprovado,
    String assinaturaBase64
) {}