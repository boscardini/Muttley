package com.example.Muttley.apresentador;

import jakarta.validation.constraints.NotBlank;

public record ApresentadorRequestDTO (
    @NotBlank String nome,
    @NotBlank String telefone,
    String cpf
) {}
