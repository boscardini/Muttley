package com.example.Muttley.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequestDTO(
    @NotBlank(message = "O nome é obrigatório")
    String nome,

    @Email(message = "E-mail inválido")
    @NotBlank(message = "O e-mail é obrigatório")
    String email,

    @NotBlank(message = "A senha é obrigatória")
    String senha,

    Perfil perfil // ADMIN ou GESTOR
) {}