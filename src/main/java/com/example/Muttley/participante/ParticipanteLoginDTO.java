package com.example.Muttley.participante;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ParticipanteLoginDTO(
    @NotBlank(message = "O CPF é obrigatório")
    String cpf,
    
    @NotNull(message = "A Data de Nascimento é obrigatória")
    LocalDate dataNascimento
) {}