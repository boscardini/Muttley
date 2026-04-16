package com.example.Muttley.aluno;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AlunoRequestDTO(
    @NotBlank String nome,
    @NotBlank String cpf,
    @NotBlank String senha,
    @Email String email,
    @NotBlank String linkedin
) {}
