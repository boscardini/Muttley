package com.example.Muttley.Palestrante;

import jakarta.validation.constraints.NotBlank;

public record PalestranteRequestDTO (
    @NotBlank String nome,
    @NotBlank String telefone,
    @NotBlank String cpf,
    @NotBlank String convite
) {}
