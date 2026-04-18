package com.example.Muttley.participante;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ParticipanteRequestDTO(
    @NotBlank String nome,
    @Email String email
) {}
