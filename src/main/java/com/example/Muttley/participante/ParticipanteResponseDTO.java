package com.example.Muttley.participante;

import java.time.LocalDate;

public record ParticipanteResponseDTO(
    Long id, 
    String nome, 
    String email, 
    String cpf, 
    LocalDate dataNascimento, 
    Integer pontosTotais
) {}