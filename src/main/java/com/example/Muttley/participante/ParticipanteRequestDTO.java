package com.example.Muttley.participante;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ParticipanteRequestDTO(
    @NotBlank(message = "O nome é obrigatório") 
    String nome,
    
    @Email(message = "E-mail inválido") 
    @NotBlank(message = "O e-mail é obrigatório") 
    String email,

    @NotBlank(message = "O CPF é obrigatório") 
    String cpf,

    @NotNull(message = "A data de nascimento é obrigatória") 
    LocalDate dataNascimento
) {}