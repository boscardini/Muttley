package com.example.Muttley.Palestrante;

import jakarta.validation.constraints.NotBlank;

public record PalestranteRequestDTO (
    @NotBlank String nome,
    @NotBlank String telefone,
    String cpf
) {}
