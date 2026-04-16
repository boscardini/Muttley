package com.example.Muttley.palestra;

import jakarta.validation.constraints.NotBlank;

public record PalestraRequestDTO(
    @NotBlank String titulo,
    @NotBlank String palestrante,
    @NotBlank String codigo
) {}